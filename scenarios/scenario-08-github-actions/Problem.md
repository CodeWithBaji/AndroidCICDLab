# Problem

A `run: ./gradlew assemble` job with no cache, no JDK pin, and no quality step "works" until it is slow, non-reproducible, or green with broken style.

## Context

GitHub Actions is an orchestrator. It should not compile Kotlin in bash.

## What to break

Change `java-version` to `"11"` and push to a branch. AGP 9 / Gradle 9 will fail at setup or at Gradle startup. That teaches pinning.
