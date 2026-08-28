package com.androidcicdlab.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import java.io.File
import java.util.Properties

/**
 * Shared Android application conventions: SDK levels, Compose, flavors, signing.
 *
 * Build types: debug, release
 * Flavor dimension "environment": dev, qa, prod
 *
 * Variant examples: devDebug, qaDebug, prodRelease
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            failBuildIfRequested()

            extensions.configure<ApplicationExtension> {
                compileSdk {
                    version = release(37)
                }

                defaultConfig {
                    minSdk = 24
                    targetSdk = 37
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                flavorDimensions += "environment"
                productFlavors {
                    create("dev") {
                        dimension = "environment"
                        applicationIdSuffix = ".dev"
                        versionNameSuffix = "-dev"
                    }
                    create("qa") {
                        dimension = "environment"
                        applicationIdSuffix = ".qa"
                        versionNameSuffix = "-qa"
                    }
                    create("prod") {
                        dimension = "environment"
                    }
                }

                buildTypes {
                    getByName("debug") {
                        isDebuggable = true
                    }
                    getByName("release") {
                        isDebuggable = false
                        optimization {
                            enable = false
                        }
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
                        configureReleaseSigning(this@with)
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                buildFeatures {
                    compose = true
                    buildConfig = true
                }

                testOptions {
                    unitTests.isIncludeAndroidResources = true
                    unitTests.all {
                        it.systemProperty(
                            "lab.failTests",
                            providers.gradleProperty("lab.failTests").orElse("false").get(),
                        )
                    }
                }
            }

            tasks.withType<Test>().configureEach {
                ignoreFailures = false
            }

            enforceCiTaskOrder()
        }
    }

    private fun Project.failBuildIfRequested() {
        val failBuild = providers.gradleProperty("lab.failBuild").orElse("false")
        if (failBuild.get() == "true") {
            error(
                "Failure lab: lab.failBuild=true. " +
                    "Unset the property (or set it to false) to restore a successful build. " +
                    "See scenarios/scenario-09-pipeline-failure.",
            )
        }
    }

    private fun Project.enforceCiTaskOrder() {
        tasks.configureEach {
            when (name) {
                "testDevDebugUnitTest" -> mustRunAfter("detekt", "ktlintCheck")
                "assembleDevDebug" -> mustRunAfter("testDevDebugUnitTest")
                "bundleProdRelease" -> mustRunAfter("testProdReleaseUnitTest")
            }
        }
    }

    private fun com.android.build.api.dsl.ApplicationBuildType.configureReleaseSigning(project: Project) {
        val keystorePropertiesFile = project.rootProject.file("keystore.properties")
        if (!keystorePropertiesFile.isFile) {
            return
        }
        val properties = Properties()
        keystorePropertiesFile.inputStream().use(properties::load)
        val storeFilePath = properties.getProperty("storeFile") ?: return
        val storeFile = File(storeFilePath).let { file ->
            if (file.isAbsolute) file else project.rootProject.file(storeFilePath)
        }
        if (!storeFile.isFile) {
            project.logger.lifecycle(
                "keystore.properties found but storeFile does not exist: ${storeFile.absolutePath}. " +
                    "Release builds will use the default debug signing config.",
            )
            return
        }
        val android = project.extensions.getByType(ApplicationExtension::class.java)
        android.signingConfigs.create("release") {
            this.storeFile = storeFile
            storePassword = properties.getProperty("storePassword")
            keyAlias = properties.getProperty("keyAlias")
            keyPassword = properties.getProperty("keyPassword")
        }
        signingConfig = android.signingConfigs.getByName("release")
    }
}
