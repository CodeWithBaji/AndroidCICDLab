package com.androidcicdlab.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Root-level diagnostic, CI, and pipeline tasks.
 *
 * Gradle owns build logic. GitHub Actions only orchestrates these tasks.
 *
 *   ./gradlew projectInfo
 *   ./gradlew ci
 *   ./gradlew pipeline
 *   ./gradlew printArtifactPaths
 */
class CiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            registerProjectInfo()
            registerCi()
            registerPipeline()
            registerPrintArtifactPaths()
            registerPrintTaskRelations()
        }
    }

    private fun Project.registerProjectInfo() {
        val projectName = name
        val projectVersion = version.toString()
        val projectGroup = group.toString()
        val javaVersion = providers.systemProperty("java.version")
        tasks.register("projectInfo") {
            group = "diagnostics"
            description = "Displays Android project information"
            val jdk = javaVersion
            doLast {
                println("Project: $projectName")
                println("Version: $projectVersion")
                println("Group: $projectGroup")
                println("JDK: ${jdk.orNull ?: "unknown"}")
            }
        }
    }

    private fun Project.registerCi() {
        tasks.register("ci") {
            group = "verification"
            description = "Local CI: quality gates, unit tests, and a debug APK"
            dependsOn(
                ":app:detekt",
                ":app:ktlintCheck",
                ":app:testDevDebugUnitTest",
                ":app:assembleDevDebug",
            )
        }
    }

    private fun Project.registerPipeline() {
        val prepareEnvironment = tasks.register("prepareEnvironment") {
            group = "pipeline"
            description = "Validates the environment before quality, test, and build"
            val javaHome = providers.environmentVariable("JAVA_HOME")
            val javaVersion = providers.systemProperty("java.version")
            doLast {
                println("prepareEnvironment")
                println("  JAVA_HOME=${javaHome.orNull ?: "(not set)"}")
                println("  java.version=${javaVersion.orNull ?: "unknown"}")
            }
        }

        val runQualityChecks = tasks.register("runQualityChecks") {
            group = "pipeline"
            description = "Runs Detekt and Ktlint after the environment is prepared"
            dependsOn(prepareEnvironment, ":app:detekt", ":app:ktlintCheck")
            doLast { println("runQualityChecks: passed") }
        }

        val runTests = tasks.register("runTests") {
            group = "pipeline"
            description = "Runs unit tests after quality checks"
            dependsOn(runQualityChecks, ":app:testDevDebugUnitTest")
            doLast { println("runTests: passed") }
        }

        val buildApplication = tasks.register("buildApplication") {
            group = "pipeline"
            description = "Assembles the default CI variant after tests"
            dependsOn(runTests, ":app:assembleDevDebug")
            doLast { println("buildApplication: app/build/outputs/apk/dev/debug/") }
        }

        val generateReport = tasks.register("generateReport") {
            group = "pipeline"
            description = "Writes a pipeline summary after the application is built"
            dependsOn(buildApplication)
            val reportFile = layout.projectDirectory.file("pipeline-report.txt")
            outputs.file(reportFile)
            doLast {
                val body = """
                    AndroidCICDLab pipeline report
                    prepareEnvironment -> runQualityChecks -> runTests -> buildApplication -> generateReport
                    APK: app/build/outputs/apk/dev/debug/
                    Tests: app/build/reports/tests/testDevDebugUnitTest/index.html
                    Detekt: app/build/reports/detekt/detekt.html
                """.trimIndent()
                reportFile.asFile.writeText(body + "\n")
                println(body)
            }
        }

        tasks.register("pipeline") {
            group = "pipeline"
            description = "Full local pipeline using a real Gradle task dependency graph"
            dependsOn(generateReport)
        }
    }

    private fun Project.registerPrintArtifactPaths() {
        tasks.register("printArtifactPaths") {
            group = "diagnostics"
            description = "Prints expected APK and AAB output locations"
            doLast {
                println(
                    """
                    Build artifacts (after the matching assemble/bundle task):
                      APK  devDebug:     app/build/outputs/apk/dev/debug/
                      APK  qaDebug:      app/build/outputs/apk/qa/debug/
                      APK  prodRelease:  app/build/outputs/apk/prod/release/
                      AAB  prodRelease:  app/build/outputs/bundle/prodRelease/
                    Test reports:
                      Unit:              app/build/reports/tests/testDevDebugUnitTest/index.html
                    Quality reports:
                      Detekt:            app/build/reports/detekt/detekt.html
                      Ktlint:            app/build/reports/ktlint/
                    """.trimIndent(),
                )
            }
        }
    }

    private fun Project.registerPrintTaskRelations() {
        tasks.register("printTaskRelations") {
            group = "diagnostics"
            description = "Explains dependsOn vs mustRunAfter vs shouldRunAfter vs finalizedBy"
            doLast {
                println(
                    """
                    dependsOn     — B cannot start until A has succeeded. A is required.
                    mustRunAfter  — if both A and B run, A runs first. B can still run alone.
                    shouldRunAfter— like mustRunAfter, but Gradle may ignore it to break cycles.
                    finalizedBy   — when A runs, B is scheduled after A (cleanup, reports).

                    CI/CD implication:
                      Ordering alone is not a contract. If a quality task is only
                      mustRunAfter tests, skipping tests also skips the ordering,
                      and quality may never run. Put quality on ci via dependsOn.
                    """.trimIndent(),
                )
            }
        }
    }
}
