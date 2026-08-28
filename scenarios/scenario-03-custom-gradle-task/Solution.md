# Solution

`ci` is registered on the root project:

```kotlin
tasks.register("ci") {
    group = "verification"
    description = "Local CI: quality gates, unit tests, and a debug APK"
    dependsOn(
        ":app:detekt",
        ":app:ktlintCheck",
        ":app:testDevDebugUnitTest",
        ":app:assembleDevDebug",
    )
}
```

`dependsOn` means those tasks are **required**. `mustRunAfter` only orders them when they already would run. `finalizedBy` is for follow-up work (reports), not gates.

The Android convention plugin adds `mustRunAfter` so tests wait for quality and assemble waits for tests when they share a graph.

## Why this matters in CI/CD

GitHub Actions should be:

```yaml
- run: ./gradlew ci
```

not a handwritten copy of the graph. If you change the pipeline, you change one Gradle task. Every workflow that calls `ci` inherits it.

## Recommendation

One `ci` task per repository. Optional extra jobs (instrumentation, Firebase) stay outside `ci` because they need devices or secrets.
