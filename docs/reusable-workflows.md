# Reusable workflows

```text
AndroidScenarioLab
        │
        └────┐

ComposeScenarioLab
        │
        └────┼────► Reusable Android CI Workflow

CoroutineScenarioLab
        │
        └────┘
```

This repository defines `.github/workflows/reusable-android-ci.yml` with `on: workflow_call`.

## Inputs

| Input | Default | Meaning |
| --- | --- | --- |
| `gradle_task` | `ci` | Gradle tasks to run |
| `build_variant` | `DevDebug` | Label in the job name / logs |
| `run_quality_checks` | `true` | Extra detekt/ktlint when `gradle_task` is not already `ci` |
| `java_version` | `17` | Temurin major version |
| `upload_apk` | `true` | Upload `debug-apk` |

## Caller example

```yaml
jobs:
  validate:
    uses: ./.github/workflows/reusable-android-ci.yml
    with:
      gradle_task: ci
      build_variant: DevDebug
      run_quality_checks: true
```

From another repository in the same org (after this workflow is published):

```yaml
jobs:
  validate:
    uses: YOUR_ORG/AndroidCICDLab/.github/workflows/reusable-android-ci.yml@v1
    with:
      gradle_task: ci
```

## Secrets inheritance

Called workflows do not see caller secrets unless `secrets: inherit` or an explicit `secrets:` map is set. This lab's reusable workflow does not need signing secrets; release/Firebase workflows do.

## Versioning and pinning

| Ref | Risk |
| --- | --- |
| `@main` | Convenience. A breaking change in the reusable workflow can fail every consumer overnight. |
| `@v1` (moving tag) | Better. Still mutable if someone force-moves the tag. |
| `@<full-sha>` | Strongest pin. Verbose. Best for production platform repos. |

**Recommendation:** publish annotated tags (`v1.0.0`) and pin consumers to a sha or to `v1` after review. Do not pin production apps to `main`.

## What belongs in the reusable workflow

Checkout, JDK, Gradle cache, invoking Gradle, uploading artifacts. Not: product-specific signing, Play publishing, or flavor rules that only one app needs — those stay in the consumer or in Gradle.
