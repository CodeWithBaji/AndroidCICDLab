package com.androidcicdlab.app.pipeline

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PipelineGateTest {
    private val gate = PipelineGate()

    @Test
    fun qualityStageBlocksBuildWhenDetektFails() {
        val report = QualityReport(
            detektPassed = false,
            ktlintPassed = true,
            testsPassed = true,
        )
        assertFalse(gate.canAdvance(PipelineStage.QUALITY, report))
        assertFalse(gate.canAdvance(PipelineStage.BUILD, report))
    }

    @Test
    fun qualityStageAllowsTestsWhenStaticAnalysisPasses() {
        val report = QualityReport(
            detektPassed = true,
            ktlintPassed = true,
            testsPassed = false,
        )
        assertTrue(gate.canAdvance(PipelineStage.QUALITY, report))
        assertFalse(gate.canAdvance(PipelineStage.TEST, report))
    }

    @Test
    fun fullSuccessAllowsEveryStage() {
        val report = QualityReport(
            detektPassed = true,
            ktlintPassed = true,
            testsPassed = true,
        )
        PipelineStage.entries.forEach { stage ->
            assertTrue(gate.canAdvance(stage, report))
        }
    }

    @Test
    fun nextStageWalksThePipelineInOrder() {
        assertEquals(PipelineStage.QUALITY, gate.nextStage(PipelineStage.PREPARE))
        assertEquals(PipelineStage.DISTRIBUTE, gate.nextStage(PipelineStage.ARTIFACT))
        assertEquals(null, gate.nextStage(PipelineStage.DISTRIBUTE))
    }

    @Test
    fun mockkCollaboratorCanOverrideGateDecision() {
        val gateMock = mockk<PipelineGate>()
        every { gateMock.canAdvance(PipelineStage.QUALITY, any()) } returns true
        val report = QualityReport(detektPassed = false, ktlintPassed = false, testsPassed = false)
        assertTrue(gateMock.canAdvance(PipelineStage.QUALITY, report))
    }
}

class ArtifactClassifierTest {
    private val classifier = ArtifactClassifier()

    @Test
    fun classifiesApkAndAab() {
        assertEquals(ArtifactKind.APK, classifier.classify("app/build/outputs/apk/dev/debug/app-dev-debug.apk"))
        assertEquals(ArtifactKind.AAB, classifier.classify("app/build/outputs/bundle/prodRelease/app-prod-release.aab"))
        assertEquals(ArtifactKind.UNKNOWN, classifier.classify("pipeline-report.txt"))
    }

    @Test
    fun releaseBundleRequiresAabAndReleasePath() {
        assertTrue(classifier.isReleaseBundle("app-prod-release.aab"))
        assertFalse(classifier.isReleaseBundle("app-dev-debug.apk"))
        assertFalse(classifier.isReleaseBundle("app-prod-release.apk"))
    }
}

class VersionLabelFormatterTest {
    private val formatter = VersionLabelFormatter()

    @Test
    fun formatsFlavorAndBuildType() {
        assertEquals("1.0.0 (devdebug)", formatter.format("1.0.0", "dev", "debug"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsBlankVersion() {
        formatter.format("  ", "prod", "release")
    }
}
