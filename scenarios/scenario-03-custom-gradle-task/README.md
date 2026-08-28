# Scenario 3 — Custom CI Gradle Task

`./gradlew ci` is the local pipeline. GitHub Actions should call this same task.

## Try

```bash
./gradlew ci
./gradlew help --task ci
./gradlew printTaskRelations
```

Graph:

```text
ci
│
├── detekt
├── ktlintCheck
├── testDevDebugUnitTest
└── assembleDevDebug
```

(Conceptually `testDebugUnitTest` — with flavors the name is `testDevDebugUnitTest`.)

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
