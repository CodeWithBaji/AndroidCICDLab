# Scenario 9 — Pipeline Failure Lab

`main` stays green. Failures are reproduced with flags or `workflow_dispatch`.

Workflow: `.github/workflows/failure-lab.yml`

## Try locally

| Failure | Command |
| --- | --- |
| A Unit test | `./gradlew testDevDebugUnitTest -Plab.failTests=true` |
| B Detekt | `./gradlew detektFailureLab` |
| C Build | `./gradlew assembleDevDebug -Plab.failBuild=true` |
| D Missing secret | GitHub only (empty `KEYSTORE_PASSWORD`) |
| E Missing artifact | GitHub only (`if-no-files-found: error` on a fake path) |

```text
Test Failed
    ↓
Gradle exits with non-zero code
    ↓
GitHub Actions job fails
    ↓
Pipeline stops
```

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
