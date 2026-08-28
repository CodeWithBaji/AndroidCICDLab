# Gradle tasks

## Discovery

```bash
./gradlew tasks
./gradlew tasks --all
./gradlew tasks --group diagnostics
./gradlew tasks --group pipeline
./gradlew tasks --group verification
```

`./gradlew help --task ci` prints the `ci` description and dependencies.

## Lab tasks

| Task | Group | What it does |
| --- | --- | --- |
| `projectInfo` | diagnostics | Prints project name and version |
| `printTaskRelations` | diagnostics | Explains dependsOn / mustRunAfter / finalizedBy |
| `printArtifactPaths` | diagnostics | Prints APK, AAB, and report locations |
| `ci` | verification | detekt + ktlintCheck + testDevDebugUnitTest + assembleDevDebug |
| `pipeline` | pipeline | Full graph through `generateReport` |
| `prepareEnvironment` | pipeline | First node of the graph |
| `runQualityChecks` | pipeline | Detekt + Ktlint |
| `runTests` | pipeline | Unit tests for `devDebug` |
| `buildApplication` | pipeline | `assembleDevDebug` |
| `generateReport` | pipeline | Writes `pipeline-report.txt` |
| `detektFailureLab` | verification | Detekt on intentional violations |
| `assembleDevDebug` | build | Default CI APK |
| `assembleQaDebug` | build | QA sideload APK |
| `bundleProdRelease` | build | Production App Bundle |

## Dependency APIs

```text
dependsOn      B cannot start until A has succeeded. A is required.
mustRunAfter   If both run, A is first. B can still run alone.
shouldRunAfter Like mustRunAfter, but Gradle may ignore it to break cycles.
finalizedBy    When A runs, B is scheduled afterwards (cleanup, reports).
```

`ci` uses `dependsOn`. Quality and tests are not optional when you run `ci`.

`mustRunAfter` is used inside the Android convention plugin so that, when those tasks are in the same graph, tests wait for Detekt/Ktlint and assemble waits for tests.

Do not call `./gradlew detekt` from inside a `doLast` action. That bypasses the task graph, breaks up-to-date checks, and hides failures from `--dry-run`.

## Lifecycle of `assembleDebug` / `assembleDevDebug`

```text
Source Code
    ↓
Compilation (Kotlin / Java)
    ↓
Resource Processing (merge, link)
    ↓
DEX (D8)
    ↓
APK Packaging
    ↓
Debug APK  →  app/build/outputs/apk/<flavor>/debug/
```

With product flavors, `assembleDebug` still exists: it builds **every** debug flavor. Prefer `assembleDevDebug` in CI so you pay for one variant.

## Configuration vs execution

Gradle has three phases: initialization (settings), configuration (build scripts and plugins), execution (selected tasks). `--dry-run` prints the execution plan without running actions. `-Plab.failBuild=true` fails during **configuration**, which is a different log signature than a failed test.
