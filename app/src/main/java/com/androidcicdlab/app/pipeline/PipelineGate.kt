package com.androidcicdlab.app.pipeline

/**
 * Ordered stages of the local and CI pipeline.
 * The home screen prints this sequence; unit tests use it to model gates.
 */
enum class PipelineStage {
    PREPARE,
    QUALITY,
    TEST,
    BUILD,
    ARTIFACT,
    DISTRIBUTE,
}

/** Kind of Android package produced by assemble/bundle. */
enum class ArtifactKind {
    APK,
    AAB,
    UNKNOWN,
}

/**
 * Snapshot of quality-gate results.
 *
 * @property detektPassed Static analysis (Detekt) succeeded.
 * @property ktlintPassed Formatting check succeeded.
 * @property testsPassed Unit tests succeeded.
 */
data class QualityReport(
    val detektPassed: Boolean,
    val ktlintPassed: Boolean,
    val testsPassed: Boolean,
) {
    /** True when Detekt, Ktlint, and tests all passed. */
    val allPassed: Boolean
        get() = detektPassed && ktlintPassed && testsPassed
}

/**
 * Decides whether a later pipeline stage may run.
 * Pure JVM logic so CI policy can be unit-tested without Gradle.
 */
class PipelineGate {
    /**
     * @return `true` when [from] is allowed to proceed given [report].
     */
    fun canAdvance(from: PipelineStage, report: QualityReport): Boolean {
        return when (from) {
            PipelineStage.PREPARE -> true
            PipelineStage.QUALITY -> report.detektPassed && report.ktlintPassed
            PipelineStage.TEST -> report.testsPassed
            PipelineStage.BUILD -> report.allPassed
            PipelineStage.ARTIFACT -> report.allPassed
            PipelineStage.DISTRIBUTE -> report.allPassed
        }
    }

    /** Next stage in declaration order, or `null` at the end of the pipeline. */
    fun nextStage(current: PipelineStage): PipelineStage? {
        val values = PipelineStage.entries
        val index = values.indexOf(current)
        return values.getOrNull(index + 1)
    }
}

/** Maps a file path to [ArtifactKind] using extension and path segments. */
class ArtifactClassifier {
    fun classify(path: String): ArtifactKind {
        val lower = path.lowercase()
        return when {
            lower.endsWith(".apk") -> ArtifactKind.APK
            lower.endsWith(".aab") -> ArtifactKind.AAB
            else -> ArtifactKind.UNKNOWN
        }
    }

    /** True only for a release-track Android App Bundle. */
    fun isReleaseBundle(path: String): Boolean {
        return classify(path) == ArtifactKind.AAB && path.contains("release", ignoreCase = true)
    }
}

/**
 * Formats the version chip on the home screen.
 *
 * Example: `1.0.0 (devdebug)`.
 */
class VersionLabelFormatter {
    fun format(versionName: String, flavor: String, buildType: String): String {
        require(versionName.isNotBlank()) { "versionName must not be blank" }
        return "$versionName ($flavor$buildType)"
    }
}
