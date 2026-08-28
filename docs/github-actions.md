# GitHub Actions

## Workflows in this repo

| File | Trigger | Role |
| --- | --- | --- |
| `ci.yml` | push + PR to `main` | Explicit CI stages (teaching) |
| `pr-validation.yml` | pull_request | Calls the reusable workflow |
| `release.yml` | tag `v*` + dispatch | Bundle / optional signing |
| `firebase-distribution.yml` | `develop` + dispatch | Optional QA distribution |
| `reusable-android-ci.yml` | `workflow_call` | Shared Android CI |
| `failure-lab.yml` | `workflow_dispatch` | Controlled failures |

## `ci.yml` stages

```text
Checkout
    ↓
Setup JDK
    ↓
Restore Gradle Cache
    ↓
Quality Checks
    ↓
Unit Tests
    ↓
Build APK
    ↓
Upload Artifact
```

Official actions used: `actions/checkout`, `actions/setup-java`, `gradle/actions/setup-gradle`, `actions/upload-artifact`.

## Exit codes

Gradle exits `0` on success and non-zero on failure. GitHub Actions fails the step, then the job, when a step exits non-zero (unless `continue-on-error: true`). That is the only contract CI needs if build logic lives in Gradle.

## Reading a failed run

1. Open the workflow run.
2. Expand the first red step — later steps may be skipped.
3. For Gradle, scroll to `What went wrong` / the failed task name.
4. Download artifacts only after the upload step succeeded.

## Branch protection (manual)

This repository does not change org/repo settings. In GitHub:

1. Settings → Branches → Add rule for `main`.
2. Require a pull request.
3. Require status checks to pass: `PR validation / Android CI (DevDebug)` and/or `Quality, test, assemble`.
4. Do not allow bypassing for administrators in production teams.

See [reusable-workflows.md](reusable-workflows.md) and [signing.md](signing.md).
