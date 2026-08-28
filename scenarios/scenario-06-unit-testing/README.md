# Scenario 6 — Unit Testing Pipeline

Production code under `pipeline/` is tested on the JVM. Gradle exit codes are what CI uses.

## Try (green)

```bash
./gradlew testDevDebugUnitTest
open app/build/reports/tests/testDevDebugUnitTest/index.html
```

## Try (red, on purpose)

```bash
./gradlew testDevDebugUnitTest -Plab.failTests=true
echo $?   # non-zero
```

Instrumentation (device/emulator, not in default `ci`):

```bash
./gradlew connectedDevDebugAndroidTest
```

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
