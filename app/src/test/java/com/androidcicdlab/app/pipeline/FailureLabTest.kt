package com.androidcicdlab.app.pipeline

import org.junit.Assert.fail
import org.junit.Test

/**
 * Intentionally fails when `-Plab.failTests=true` is passed to Gradle.
 *
 *   ./gradlew testDevDebugUnitTest -Plab.failTests=true
 *
 * Gradle then exits non-zero and any GitHub Actions job that invoked the
 * task fails. Keep lab.failTests=false on main.
 */
class FailureLabTest {
    @Test
    fun failsOnlyWhenLabFlagEnabled() {
        val shouldFail = System.getProperty("lab.failTests", "false").equals("true", ignoreCase = true)
        if (shouldFail) {
            fail(
                "Failure lab: lab.failTests=true. " +
                    "Gradle exits non-zero, so GitHub Actions marks the job as failed. " +
                    "See scenarios/scenario-09-pipeline-failure.",
            )
        }
    }
}
