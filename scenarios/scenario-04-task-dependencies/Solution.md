# Solution

Each stage is a real task. Later stages `dependsOn` earlier stages **and** the Gradle tasks that do the work:

- `runQualityChecks` → `prepareEnvironment`, `:app:detekt`, `:app:ktlintCheck`
- `runTests` → `runQualityChecks`, `:app:testDevDebugUnitTest`
- `buildApplication` → `runTests`, `:app:assembleDevDebug`
- `generateReport` → `buildApplication`
- `pipeline` → `generateReport`

App-level `mustRunAfter` keeps AGP tasks ordered when they share the graph.

```bash
./gradlew pipeline
```

Writes `pipeline-report.txt` (gitignored) with report paths.

## Recommendation

If you need a stage you can run in isolation, it must be a task, not a comment in YAML.
