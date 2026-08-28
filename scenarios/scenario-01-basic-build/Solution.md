# Solution

Use Gradle as the source of truth for "what to build" and "where it goes".

```text
Source Code
    ↓
Compilation
    ↓
Resource Processing
    ↓
DEX
    ↓
APK Packaging
    ↓
Debug APK
```

Commands:

```bash
./gradlew assembleDevDebug     # one CI-sized APK
./gradlew assembleDebug        # every debug flavor
./gradlew bundleProdRelease    # Play-shaped output
./gradlew printArtifactPaths   # documented locations
```

Inspect:

```bash
./gradlew :app:assembleDevDebug --dry-run
ls app/build/outputs/apk/dev/debug/
```

CI should call these tasks, then upload the path Gradle already owns. Do not shell-script compilation.

## Recommendation

Default CI variant is `devDebug`. Use `bundleProdRelease` only on release jobs.
