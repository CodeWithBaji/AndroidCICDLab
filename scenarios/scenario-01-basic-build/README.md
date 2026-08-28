# Scenario 1 — Basic Android Build

Run `./gradlew assembleDevDebug` (or `assembleDebug`) and inspect the outputs. This scenario is about the **lifecycle**, not about adding features.

## Try

```bash
./gradlew tasks
./gradlew assembleDevDebug
./gradlew bundleProdRelease
./gradlew clean
./gradlew printArtifactPaths
```

APK: `app/build/outputs/apk/dev/debug/`  
AAB: `app/build/outputs/bundle/prodRelease/`

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
