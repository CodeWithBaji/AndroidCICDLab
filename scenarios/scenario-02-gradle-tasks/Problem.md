# Problem

`./gradlew tasks` is noisy. Teams then hard-code task names in CI without understanding groups, descriptions, or the difference between "this must run" and "this should run after".

## Context

A Gradle task has: name, group, description, actions (`doLast`), and relationships (`dependsOn`, `mustRunAfter`, `shouldRunAfter`, `finalizedBy`).

Android already registered `assembleDevDebug`. This lab adds diagnostic tasks so you can experiment without reading AGP source.

## What to break

Rename the `projectInfo` group in `CiConventionPlugin` and run `./gradlew tasks --group diagnostics` — it disappears from that listing. CI that calls `./gradlew projectInfo` still works; discovery does not.
