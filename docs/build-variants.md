# Build variants

## Matrix

```text
Build Types
│
├── debug
└── release

Product Flavors
│
├── dev
├── qa
└── prod
```

Flavor dimension: `environment`.

Generated variants include `devDebug`, `qaDebug`, `prodRelease`, and the rest of the 3×2 matrix.

## Commands

```bash
./gradlew assembleDevDebug
./gradlew assembleQaDebug
./gradlew assembleProdRelease
./gradlew bundleProdRelease
./gradlew testDevDebugUnitTest
```

`assembleDebug` builds all debug flavors. `ci` uses `devDebug` only.

## When to use each

| Variant | Use |
| --- | --- |
| `devDebug` | Local development and default CI. `applicationId` suffix `.dev`. Fast, debuggable. |
| `qaDebug` | QA sideload / Firebase App Distribution. Same debuggability, different id. |
| `prodRelease` | Play Store / internal app sharing. No suffix. Sign with the upload keystore. |

Flavor resources override `app_name` and `environment_name` under `app/src/<flavor>/res`.

## Source sets

```text
app/src/main/          shared
app/src/dev/           dev-only resources
app/src/qa/
app/src/prod/
app/src/test/          JVM unit tests
app/src/androidTest/   instrumentation / Compose UI tests
```

## CI recommendation

Pin **one** variant per job. Building the full matrix on every PR is usually waste. Nightly or release jobs can add `qaDebug` and `prodRelease`.
