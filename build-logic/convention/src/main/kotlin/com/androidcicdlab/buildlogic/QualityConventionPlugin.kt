package com.androidcicdlab.buildlogic

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jlleitschuh.gradle.ktlint.KtlintExtension

/**
 * Detekt + Ktlint quality gates.
 *
 * Default `./gradlew detekt ktlintCheck` stays green on main.
 * Intentional violations live in QualityViolationLab.kt and are excluded unless
 * `-Plab.failQuality=true`. Run `./gradlew detektFailureLab` to fail on purpose.
 *
 * Detekt 2.x (`dev.detekt`) is required here: 1.23 cannot parse JDK 25, which is
 * what Gradle uses on this lab's toolchain.
 */
class QualityConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("dev.detekt")
            pluginManager.apply("org.jlleitschuh.gradle.ktlint")

            val failQuality = providers.gradleProperty("lab.failQuality").orElse("false")
                .get() == "true"
            val detektConfig = rootProject.file("config/detekt/detekt.yml")
            val violationGlob = "**/lab/QualityViolationLab.kt"

            extensions.configure<DetektExtension> {
                buildUponDefaultConfig.set(true)
                allRules.set(false)
                parallel.set(true)
                ignoreFailures.set(false)
                config.setFrom(detektConfig)
                source.setFrom("src/main/java", "src/main/kotlin")
            }

            tasks.withType<Detekt>().configureEach {
                jvmTarget.set("17")
                reports {
                    html.required.set(true)
                    checkstyle.required.set(true)
                    sarif.required.set(false)
                    markdown.required.set(false)
                }
                if (!failQuality && name != "detektFailureLab") {
                    exclude(violationGlob)
                }
            }

            tasks.register<Detekt>("detektFailureLab") {
                group = "verification"
                description = "Failure lab: runs Detekt against intentional quality violations"
                buildUponDefaultConfig.set(true)
                allRules.set(false)
                jvmTarget.set("17")
                config.setFrom(detektConfig)
                setSource(files("src/main/java/com/androidcicdlab/app/lab"))
                include("**/QualityViolationLab.kt")
                ignoreFailures.set(false)
            }

            extensions.configure<KtlintExtension> {
                android.set(true)
                ignoreFailures.set(false)
                filter {
                    if (!failQuality) {
                        exclude(violationGlob)
                    }
                }
            }
        }
    }
}
