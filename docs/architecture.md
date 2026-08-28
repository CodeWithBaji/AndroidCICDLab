# Architecture

Gradle owns **build logic**. GitHub Actions owns **orchestration**. Reusable workflows own **standards**.

```text
Developer
    │
    ▼
Git Push / Pull Request
    │
    ▼
GitHub Actions
    │
    ▼
Environment Setup
    │
    ▼
./gradlew ci
    │
    ├── Code Quality
    │      ├── Detekt
    │      └── Ktlint
    │
    ├── Testing
    │      ├── Unit Tests
    │      └── UI Tests
    │
    ├── Build
    │      ├── APK
    │      └── AAB
    │
    ▼
Artifacts
    │
    ├── GitHub Artifact
    │
    ├── Firebase App Distribution
    │
    └── Play Store
```

## Module map

| Path | Role |
| --- | --- |
| `app/` | Single Android application. Flavors and tests live here. |
| `build-logic/convention/` | Convention plugins: Android defaults, quality gates, CI tasks. |
| `gradle/libs.versions.toml` | Version Catalog — one place for versions. |
| `config/detekt/detekt.yml` | Detekt rule set used by `./gradlew detekt`. |
| `.github/workflows/` | Orchestration only. Prefer `./gradlew ci` over duplicating logic in YAML. |
| `scenarios/` | Problem → broken/simple path → working path → trade-offs. |
| `scripts/` | Host checks that Gradle cannot reasonably own. |

## Convention plugins

| Plugin id | Applied to | Responsibility |
| --- | --- | --- |
| `androidcicdlab.android.application` | `:app` | SDK, Compose, flavors, signing, task order |
| `androidcicdlab.quality` | `:app` | Detekt, Ktlint, `detektFailureLab` |
| `androidcicdlab.ci` | root | `projectInfo`, `ci`, `pipeline`, diagnostics |

## Why one app module

Scenarios are Gradle, CI, and documentation — not extra Android modules. Extra modules would hide the variant graph and inflate assemble times without teaching more about CI/CD.

## Configuration cache

`org.gradle.configuration-cache=true` is on. Custom tasks capture values at configuration time (`project.name`, `version`) instead of touching `Project` inside `doLast`. That is required for a modern pipeline.

## Related

- [gradle-tasks.md](gradle-tasks.md)
- [build-variants.md](build-variants.md)
- [github-actions.md](github-actions.md)
- [reusable-workflows.md](reusable-workflows.md)
- [integration-guide.md](integration-guide.md)
