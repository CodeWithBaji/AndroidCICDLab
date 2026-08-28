# Problem

A CI system does not "read" test reports. It reads the **process exit code**. If Gradle is configured with `ignoreFailures = true`, the HTML report is red and the job is green. That is a false pipeline.

## Context

- Passing tests: `PipelineGateTest`, `ArtifactClassifierTest`, `VersionLabelFormatterTest` (JUnit + MockK)
- Intentional failure: `FailureLabTest` when `-Plab.failTests=true`
- UI tests: `LabHomeScreenTest` (Compose, instrumentation)

## What to break

```bash
./gradlew testDevDebugUnitTest -Plab.failTests=true
```

Watch Gradle print `BUILD FAILED`. That is the same signal GitHub Actions uses.
