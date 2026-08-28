# Solution

The lab registers:

```kotlin
tasks.register("projectInfo") {
    group = "diagnostics"
    description = "Displays Android project information"
    // capture name/version at configuration time (configuration cache)
    doLast { /* print */ }
}
```

Run:

```bash
./gradlew projectInfo
```

Expected: `Project: AndroidCICDLab` and `Version: 1.0.0`.

Read `./gradlew printTaskRelations` for the four relationship APIs. See [docs/gradle-tasks.md](../../docs/gradle-tasks.md).

## Recommendation

Every custom task gets a `group` and `description`. CI-facing tasks live in `verification` or `pipeline`. Diagnostics stay out of `ci` unless they are cheap and always useful.
