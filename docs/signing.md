# Signing and secrets

```text
GitHub Secrets
      ↓
CI Environment
      ↓
Decode Keystore
      ↓
Signing Configuration
      ↓
Signed APK / AAB
```

## Never commit

- `keystore.properties`
- `*.jks` / `*.keystore`
- API keys, `google-services.json` with private keys, Play service accounts

The example file `keystore.properties.example` is safe to commit. Copy it locally.

## Local signing

1. Create an upload keystore (once), store the file in a password manager / secrets vault — not git.
2. `cp keystore.properties.example keystore.properties`
3. Point `storeFile` at the keystore.
4. `./gradlew bundleProdRelease`

If `keystore.properties` is missing, release builds still assemble using the Android debug keystore. That keeps forks and CI green. Production uploads require the real upload key.

## CI signing

GitHub repository secrets (placeholders — do not put real values in git):

| Secret | Purpose |
| --- | --- |
| `KEYSTORE_BASE64` | `base64` of the `.jks` file |
| `KEYSTORE_PASSWORD` | Keystore password |
| `KEY_ALIAS` | Key alias |
| `KEY_PASSWORD` | Key password |

Encode locally (example):

```bash
base64 -i upload-keystore.jks | pbcopy
```

`release.yml` decodes to `upload-keystore.jks` and writes `keystore.properties` on the runner. The runner is ephemeral; the files are not committed.

## Local vs CI

| | Local | CI |
| --- | --- | --- |
| Keystore | File on disk, gitignored | Base64 secret, decoded each run |
| Properties | `keystore.properties` | Generated in the workflow |
| Debug builds | Android debug key | Android debug key |
| Release uploads | Upload key | Upload key from secrets |

## Why secrets must not be committed

Git history is forever. Rotating a leaked upload key means coordinating with Play Console. Treat CI secrets like production keys: least privilege, org-level where possible, and never printed in logs (GitHub masks secret values).
