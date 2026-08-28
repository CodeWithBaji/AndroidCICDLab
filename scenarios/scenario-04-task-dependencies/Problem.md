# Problem

A common anti-pattern:

```kotlin
doLast {
    project.exec { commandLine("./gradlew", "detekt") }
}
```

That hides work from the task graph. `--dry-run` lies. Parallelism breaks. Up-to-date checks never run. Failures look like "exec failed" instead of "Detekt found issues".

## Context

CI needs a visible, ordered graph so you can run `runQualityChecks` alone while debugging, or `pipeline` for the full path.

## What to break

In `CiConventionPlugin`, make `runTests` stop depending on `runQualityChecks` and run `./gradlew pipeline --dry-run`. Tests can start before quality. That is a real CI bug: a red Detekt report after a green test log.
