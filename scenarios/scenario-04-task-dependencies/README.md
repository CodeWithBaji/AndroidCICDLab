# Scenario 4 — Task Dependency Graph

`./gradlew pipeline` runs a named graph, not a shell script of task names inside `doLast`.

## Try

```bash
./gradlew pipeline
./gradlew pipeline --dry-run
cat pipeline-report.txt
```

```text
prepareEnvironment
        ↓
runQualityChecks
        ↓
runTests
        ↓
buildApplication
        ↓
generateReport
```

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
