# Scenario 12 — Secrets and Signing

```text
GitHub Secrets → CI Environment → Decode Keystore → Signing Configuration → Signed APK / AAB
```

Safe files:

- `keystore.properties.example`
- [docs/signing.md](../../docs/signing.md)

Never commit `keystore.properties` or `*.jks`.

## Try

```bash
cp keystore.properties.example keystore.properties
# fill in local values, do not commit
./gradlew bundleProdRelease
```

Without a keystore, release still assembles with the debug key so public CI stays green.

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
