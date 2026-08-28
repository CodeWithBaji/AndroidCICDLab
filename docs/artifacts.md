# Artifacts

## Pipeline

```text
Build APK
    ↓
Locate APK
    ↓
Upload Artifact
    ↓
Download From GitHub Actions
```

Default CI artifact name: `debug-apk`  
Default path: `app/build/outputs/apk/dev/debug/*.apk`

```bash
./gradlew assembleDevDebug
./gradlew printArtifactPaths
```

## Vocabulary

| Term | Meaning |
| --- | --- |
| **Build artifact** | Any file a build produces (APK, AAB, reports, mapping.txt). |
| **GitHub Actions artifact** | A zip GitHub stores on the workflow run. Not a store listing. |
| **GitHub Release** | A versioned page with notes and attached files, usually from a tag. |
| **APK** | Installable package. Sideload, Firebase, internal tracks. |
| **AAB** | Play Store upload format. The store generates split APKs. |

## Locations

| Output | Path |
| --- | --- |
| devDebug APK | `app/build/outputs/apk/dev/debug/` |
| qaDebug APK | `app/build/outputs/apk/qa/debug/` |
| prodRelease APK | `app/build/outputs/apk/prod/release/` |
| prodRelease AAB | `app/build/outputs/bundle/prodRelease/` |
| Unit test HTML | `app/build/reports/tests/testDevDebugUnitTest/index.html` |
| Detekt HTML | `app/build/reports/detekt/detekt.html` |

## Play Store vs Firebase vs GitHub

- GitHub artifact: engineers debugging a CI run.
- Firebase App Distribution: QA testers, not customers.
- Play Store (AAB): production or closed testing tracks.
