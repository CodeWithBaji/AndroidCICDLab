# Scenario 7 — Build Variants

Flavors (`dev`, `qa`, `prod`) × build types (`debug`, `release`).

## Try

```bash
./gradlew assembleDevDebug
./gradlew assembleQaDebug
./gradlew bundleProdRelease
```

Convention plugin: `AndroidApplicationConventionPlugin`  
Resources: `app/src/dev`, `app/src/qa`, `app/src/prod`

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
