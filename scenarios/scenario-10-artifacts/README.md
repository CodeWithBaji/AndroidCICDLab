# Scenario 10 — Build Artifact Pipeline

```text
Build APK → Locate APK → Upload Artifact → Download From GitHub Actions
```

Artifact name: `debug-apk`

## Try

```bash
./gradlew assembleDevDebug
ls app/build/outputs/apk/dev/debug/
```

On GitHub: Actions → CI run → Artifacts → `debug-apk`.

See [docs/artifacts.md](../../docs/artifacts.md).

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
