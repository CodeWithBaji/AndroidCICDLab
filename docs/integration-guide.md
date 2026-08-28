# Integration with other Scenario Labs

Target repositories:

```text
AndroidScenarioLab
KotlinScenarioLab
CoroutineScenarioLab
ComposeScenarioLab
```

Add CI/CD in levels. Do not start with reusable workflows.

## Level 1 — Basic CI

```text
./gradlew test
./gradlew assembleDebug
```

Enough for a learning project. Proves the wrapper, JDK, and unit tests work.

## Level 2 — Quality gates

```text
./gradlew detekt
./gradlew ktlintCheck
```

Add Version Catalog entries and a Detekt config. Run these **before** assemble in CI.

## Level 3 — Unified CI task

```text
./gradlew ci
```

Copy the idea from `androidcicdlab.ci`: one task whose `dependsOn` graph **is** the pipeline. Developers and CI run the same command.

## Level 4 — GitHub Actions

```text
Git Push
    ↓
GitHub Actions
    ↓
./gradlew ci
```

YAML only checks out, sets up JDK 17, and calls Gradle. Copy `ci.yml` as a starting point.

## Level 5 — Shared reusable workflow

```text
Scenario Lab Repository
        ↓
Reusable GitHub Workflow
        ↓
Standard Android CI Pipeline
```

Point the lab at this repository's `reusable-android-ci.yml` pinned to a tag/sha. Pass `gradle_task: ci` once that project defines `ci`.

## Mapping tasks to non-Android labs

| Lab | Level 1 | Level 3 equivalent |
| --- | --- | --- |
| KotlinScenarioLab | `./gradlew test` | `./gradlew ci` → check + test |
| CoroutineScenarioLab | `./gradlew test` | same |
| ComposeScenarioLab | `./gradlew testDebugUnitTest assembleDebug` | same as this repo |
| AndroidScenarioLab | same as this repo | copy `ci` + flavors if needed |

Kotlin-only repos omit `assemble*` and Android instrumentation. They can still reuse the **shape** of the workflow (checkout, JDK, Gradle, quality, test, upload reports).
