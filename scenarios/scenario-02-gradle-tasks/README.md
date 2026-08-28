# Scenario 2 — Understanding Gradle Tasks

Tasks are the unit of work. Groups and descriptions are how humans find them. Dependencies are how Gradle orders them.

## Try

```bash
./gradlew tasks
./gradlew tasks --group diagnostics
./gradlew projectInfo
./gradlew printTaskRelations
./gradlew help --task projectInfo
```

`projectInfo` is registered in `build-logic` (`CiConventionPlugin`) and prints name + version.

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
