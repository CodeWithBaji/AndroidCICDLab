# Problem

`./gradlew assembleDebug` feels like one command. It is not one action. It is a graph of tasks: compile Kotlin, merge resources, convert class files to DEX, package an APK, and write it under `app/build/outputs`.

Without seeing that graph, CI YAML tends to grow random `run:` steps ("zip this folder", "find the APK") that duplicate Gradle.

## Context

Android Gradle Plugin registers hundreds of tasks per variant. Product flavors multiply them (`assembleDevDebug`, `assembleQaDebug`, …). `assembleDebug` with flavors builds **all** debug variants.

## What to break

```bash
./gradlew assembleDevDebug
rm -rf app/build
./gradlew assembleDevDebug --dry-run
```

`--dry-run` prints the plan. After `clean`, the plan is large. On the second assemble, many tasks are UP-TO-DATE.

## Observe

1. First assemble time vs second assemble time.
2. Where the APK actually lands.
3. That `bundleProdRelease` writes an AAB, not an APK.
