# Problem

CI YAML that lists five Gradle invocations will drift from what developers run locally. Someone will skip Detekt on the laptop and only discover it in GitHub Actions.

## Context

```text
Code Quality
      ↓
Unit Tests
      ↓
Build Debug APK
```

That sequence is **product policy**. It belongs in Gradle, where it is versioned with the code and runnable offline.

## What to break

Comment out `dependsOn(":app:detekt")` in `CiConventionPlugin` and run `./gradlew ci`. Tests and APK still run. Quality is no longer a gate. That is how production pipelines silently rot.
