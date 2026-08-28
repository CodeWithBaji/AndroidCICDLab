# AndroidCICDLab

A **scenario lab** for Android CI/CD: Gradle’s task graph, quality gates, variants, GitHub Actions, artifacts, signing, and reusable workflows.

This is not a sample app with a pipeline bolted on. It is a laboratory you can **run, observe, break, debug, fix, and compare**.

```text
Run → Observe → Break → Debug → Fix → Compare
```

---

## 1. Project Overview

**AndroidCICDLab** is a production-shaped Android application (Kotlin, Jetpack Compose, Material 3) plus:

- Gradle Kotlin DSL and a Version Catalog
- Convention plugins in `build-logic`
- Custom tasks: `projectInfo`, `ci`, `pipeline`
- Detekt + Ktlint
- JUnit + MockK unit tests and Compose instrumentation tests
- GitHub Actions for CI, PR validation, release, Firebase (optional), and a failure lab
- Fourteen documented scenarios

The app module is a Compose shell that shows the current **variant** on a purple (`#6200EE`) gradient. The learning surface is Gradle + CI, not product features.

---

## 2. Why This Lab Exists

Lead Android interviews and real platform work fail in the same places: people can write Compose but cannot explain why `ci` must `dependsOn` Detekt, why an APK path changed after flavors, or why GitHub Actions is not the build system.

This repository exists so you can practice those failures **without** leaving `main` permanently red.

---

## 3. What You Will Learn

- Gradle build lifecycle and task discovery
- Custom tasks and a real dependency graph
- Build types, product flavors, APK vs AAB
- Static analysis as a gate
- Unit vs instrumentation tests and Gradle exit codes
- GitHub Actions orchestration, artifacts, PR checks
- Secrets, signing, Firebase as CD (not CI)
- Reusable workflows and how other Scenario Labs adopt them

---

## 4. Prerequisites and setup

### Prerequisites

| Tool | Version / notes |
| --- | --- |
| JDK | **17 or newer**. Gradle 9 and AGP 9 will not start on JDK 11. |
| Android SDK | Installed via Android Studio. `ANDROID_HOME` or `ANDROID_SDK_ROOT` is optional for JVM unit tests; required for emulators. |
| Git | To clone and to push for GitHub Actions. |
| GitHub account | Only if you want to run the workflows. |
| Upload keystore / Firebase | Optional. Public CI stays green without them. |

macOS / Homebrew example:

```bash
brew install openjdk@17
export JAVA_HOME="$(brew --prefix openjdk@17)/libexec/openjdk.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"
```

Confirm:

```bash
java -version
```

### Setup steps

1. **Clone the repository**

   ```bash
   git clone <your-fork-or-remote-url> AndroidCICDLab
   cd AndroidCICDLab
   ```

2. **Verify the host**

   ```bash
   chmod +x scripts/*.sh gradlew
   ./scripts/verify-environment.sh
   ```

3. **Open the project**
   - Android Studio: **File → Open** and select the `AndroidCICDLab` directory (the folder that contains `settings.gradle.kts`).
   - Wait for Gradle sync. The first sync downloads the wrapper (Gradle 9.5) and dependencies.

4. **Select a run configuration**
   - Build variant: **devDebug** (Build Variants tool window). That is the default CI flavor.
   - Target: an emulator (API 24+) or a physical device.

5. **Run the app**
   - Studio: Run `app`.
   - CLI:

     ```bash
     ./gradlew installDevDebug
     ```

     The home screen uses primary `#6200EE` with a purple gradient wash and shows the current variant.

6. **Run the same pipeline CI uses**

   ```bash
   ./gradlew ci
   ./scripts/ci-summary.sh
   ```

   This runs Detekt, Ktlint, `testDevDebugUnitTest`, and `assembleDevDebug`.

7. **Push to GitHub and watch Actions** (see [§9](#9-github-actions))

### Troubleshooting

| Symptom | What to do |
| --- | --- |
| `Unsupported class file major version` / Gradle fails at startup | Point `JAVA_HOME` at JDK 17+. |
| `SDK location not found` | Create `local.properties` with `sdk.dir=/path/to/Android/sdk` (this file is gitignored). Android Studio writes it automatically. |
| Detekt fails with a JVM target error | This lab pins Detekt 2 and `jvmTarget` 17. Do not revert to Detekt 1.23 on JDK 25. |
| Quality or tests fail on a clean checkout | You may have leftover `-Plab.failTests=true` / `lab.failQuality=true`. Those flags must stay `false` on `main`. |

---

## 5. Project Architecture

```text
AndroidCICDLab
│
├── app                          # Single Android application
├── build-logic/convention       # Convention plugins (Android, quality, CI tasks)
├── gradle/libs.versions.toml    # Version Catalog
├── config/detekt                # Detekt rules
├── scripts                      # Host environment helpers
├── .github/workflows            # Orchestration
├── docs                         # Deep dives
└── scenarios                    # Problem / Solution / Tradeoffs
```

```text
Developer
    │
    ▼
Git Push / Pull Request
    │
    ▼
GitHub Actions
    │
    ▼
Environment Setup
    │
    ▼
./gradlew ci
    │
    ├── Code Quality
    │      ├── Detekt
    │      └── Ktlint
    │
    ├── Testing
    │      ├── Unit Tests
    │      └── UI Tests
    │
    ├── Build
    │      ├── APK
    │      └── AAB
    │
    ▼
Artifacts
    │
    ├── GitHub Artifact
    │
    ├── Firebase App Distribution
    │
    └── Play Store
```

Principle:

> CI/CD should not contain all Android build logic.

```text
Gradle              = Build Logic
GitHub Actions      = Orchestration
CI/CD Platform      = Reusable Standards + Automation
```

Details: [docs/architecture.md](docs/architecture.md)

---

## 6. Scenario Roadmap

```text
Scenario 1
Basic Android Build
        ↓
Scenario 2
Understand Gradle Tasks
        ↓
Scenario 3
Create Custom Gradle Tasks
        ↓
Scenario 4
Build Task Dependency Graph
        ↓
Scenario 5
Add Quality Gates
        ↓
Scenario 6
Run Unit Tests
        ↓
Scenario 7
Manage Build Variants
        ↓
Scenario 8
Create GitHub Actions CI
        ↓
Scenario 9
Debug Pipeline Failures
        ↓
Scenario 10
Upload Build Artifacts
        ↓
Scenario 11
Validate Pull Requests
        ↓
Scenario 12
Manage Secrets and Signing
        ↓
Scenario 13
Distribute Using Firebase
        ↓
Scenario 14
Create Reusable CI/CD Workflows
        ↓
FINAL
Integrate CI/CD Across All Scenario Labs
```

| # | Folder | Topic |
| --- | --- | --- |
| 1 | [scenario-01-basic-build](scenarios/scenario-01-basic-build) | Lifecycle of assemble |
| 2 | [scenario-02-gradle-tasks](scenarios/scenario-02-gradle-tasks) | Tasks, groups, `projectInfo` |
| 3 | [scenario-03-custom-gradle-task](scenarios/scenario-03-custom-gradle-task) | `./gradlew ci` |
| 4 | [scenario-04-task-dependencies](scenarios/scenario-04-task-dependencies) | `./gradlew pipeline` |
| 5 | [scenario-05-quality-gates](scenarios/scenario-05-quality-gates) | Detekt / Ktlint |
| 6 | [scenario-06-unit-testing](scenarios/scenario-06-unit-testing) | Tests and exit codes |
| 7 | [scenario-07-build-variants](scenarios/scenario-07-build-variants) | Flavors × build types |
| 8 | [scenario-08-github-actions](scenarios/scenario-08-github-actions) | `ci.yml` |
| 9 | [scenario-09-pipeline-failure](scenarios/scenario-09-pipeline-failure) | Controlled failures |
| 10 | [scenario-10-artifacts](scenarios/scenario-10-artifacts) | `debug-apk` |
| 11 | [scenario-11-pr-validation](scenarios/scenario-11-pr-validation) | PR checks |
| 12 | [scenario-12-secrets-and-signing](scenarios/scenario-12-secrets-and-signing) | Keystore / secrets |
| 13 | [scenario-13-firebase-distribution](scenarios/scenario-13-firebase-distribution) | CD vs CI |
| 14 | [scenario-14-reusable-workflows](scenarios/scenario-14-reusable-workflows) | `workflow_call` |

Each scenario has `README.md`, `Problem.md`, `Solution.md`, and `Tradeoffs.md`.

---

## 7. Running Gradle Tasks

```bash
./gradlew tasks
./gradlew tasks --group diagnostics
./gradlew tasks --group pipeline
./gradlew projectInfo
./gradlew printTaskRelations
./gradlew printArtifactPaths
./gradlew assembleDevDebug
./gradlew assembleQaDebug
./gradlew bundleProdRelease
./gradlew clean
```

See [docs/gradle-tasks.md](docs/gradle-tasks.md) and [docs/build-variants.md](docs/build-variants.md).

---

## 8. Running CI Locally

```bash
./gradlew ci
./gradlew pipeline
./scripts/ci-summary.sh
```

`ci` is the same graph GitHub should invoke:

```text
detekt + ktlintCheck → testDevDebugUnitTest → assembleDevDebug
```

---

## 9. GitHub Actions

How to **test this repository on GitHub**:

1. Create an empty GitHub repo (public is fine). CI needs **no secrets**.
2. Push `main`:

   ```bash
   git init
   git add .
   git commit -m "Add AndroidCICDLab, a Gradle-first CI/CD scenario lab."
   git branch -M main
   git remote add origin git@github.com:YOUR_USER/AndroidCICDLab.git
   git push -u origin main
   ```

3. Open **Actions**. The **CI** workflow runs on that push (`ci.yml`).
4. When it is green, download the **debug-apk** artifact from the run.
5. Open a pull request targeting `main` (a one-line README change is enough). **CI** and **PR validation** should both report.
6. Optional: **Actions → Failure lab → Run workflow** and pick `unit-test`, `detekt`, `build`, `missing-secret`, or `missing-artifact`. This does not break `main`.
7. Optional: **Settings → Branches** → protect `main` and require `PR validation / Android CI (DevDebug)` and/or `Quality, test, assemble`. This repo does not turn that on for you.

| Workflow | Purpose |
| --- | --- |
| `ci.yml` | Annotated CI on push/PR to `main` |
| `pr-validation.yml` | Reusable-workflow PR check |
| `release.yml` | Tag/`v*` bundle + optional signing |
| `firebase-distribution.yml` | Optional QA CD |
| `reusable-android-ci.yml` | `workflow_call` for other repos |
| `failure-lab.yml` | Dispatch-only failures |

[docs/github-actions.md](docs/github-actions.md)

---

## 10. Failure Labs

Do not leave `main` broken. Use flags or **Actions → Failure lab → Run workflow**.

```bash
./gradlew testDevDebugUnitTest -Plab.failTests=true
./gradlew detektFailureLab
./gradlew assembleDevDebug -Plab.failBuild=true
./gradlew detekt ktlintCheck -Plab.failQuality=true
```

[scenarios/scenario-09-pipeline-failure](scenarios/scenario-09-pipeline-failure)

---

## 11. Artifact Pipeline

```bash
./gradlew assembleDevDebug
```

Upload name: **debug-apk** → `app/build/outputs/apk/dev/debug/*.apk`

[docs/artifacts.md](docs/artifacts.md)

---

## 12. Build Variants

```text
devDebug    local + default CI
qaDebug     QA / Firebase
prodRelease Play-oriented AAB (`bundleProdRelease`)
```

---

## 13. Signing and Secrets

Copy `keystore.properties.example` locally. Never commit keystores or passwords.

GitHub secrets (placeholders): `KEYSTORE_BASE64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD`.

[docs/signing.md](docs/signing.md)

---

## 14. Firebase Distribution

Optional. CI does not require Firebase. CD workflow skips upload when secrets are absent.

[scenarios/scenario-13-firebase-distribution](scenarios/scenario-13-firebase-distribution)

---

## 15. Reusable Workflows

`pr-validation.yml` calls `reusable-android-ci.yml` with `gradle_task`, `build_variant`, `run_quality_checks`.

From **another repository** in the same GitHub account/org (after this lab is pushed):

```yaml
# other-app/.github/workflows/pr.yml
jobs:
  validate:
    uses: YOUR_USER/AndroidCICDLab/.github/workflows/reusable-android-ci.yml@v1
    with:
      gradle_task: ci
      build_variant: DevDebug
      run_quality_checks: true
      upload_apk: true
```

Pin to a tag or commit SHA in real apps, not `@main`. Kotlin-only repos should set `upload_apk: false` (there is no APK).

[docs/reusable-workflows.md](docs/reusable-workflows.md)

---

## 16. Using this lab in other projects

Do **not** copy the Compose app. Copy the **Gradle + Actions shape**.

| Copy into the other repo | Leave in this lab |
| --- | --- |
| `./gradlew ci` task graph | Home screen / purple UI |
| Detekt + Ktlint as gates | `QualityViolationLab.kt` and fail flags |
| `.github/workflows/ci.yml` or the reusable workflow | Flavors, unless you need them |
| `keystore.properties.example` | Real keystores and secrets |

Adopt in levels (same list for `AndroidScenarioLab`, `KotlinScenarioLab`, `CoroutineScenarioLab`, `ComposeScenarioLab`):

1. `./gradlew test` and `assembleDebug` (or `test` only on JVM labs).
2. Add Detekt + Ktlint; run them before tests.
3. Add a root `ci` task that `dependsOn` quality, tests, and assemble — developers and CI run the same command:

   ```kotlin
   tasks.register("ci") {
       group = "verification"
       dependsOn("detekt", "ktlintCheck", "testDebugUnitTest", "assembleDebug")
   }
   ```

   Use that project's real task names (`testDevDebugUnitTest`, `assembleQaDebug`, …).

4. GitHub Actions only checks out, sets up JDK 17, and runs `./gradlew ci`.
5. When two or more Android repos need the same bar, call this lab's reusable workflow (section 15).

[docs/integration-guide.md](docs/integration-guide.md)

---

## 17. Architecture Decisions

| Decision | Why |
| --- | --- |
| One `app` module | Scenarios are Gradle/CI, not extra APKs |
| Convention plugins | Shared Android/quality/CI setup without copy-paste |
| Flavors `dev`/`qa`/`prod` | Real variant names in CI and docs |
| `ci` on the root project | `./gradlew ci` matches Actions |
| Failure flags default false | Public `main` stays green |
| Explicit `ci.yml` + reusable PR workflow | Teach both styles |

---

## 18. Trade-offs

- **Staged YAML vs one `./gradlew ci` step:** the former is easier to teach; the latter does not drift. This repo includes both.
- **Detekt 2.x (`dev.detekt`)** is used because Detekt 1.23 cannot parse JDK 25 (this lab’s Gradle JVM). Interviewers still know the 1.23 plugin id `io.gitlab.arturbosch.detekt`; the *role* of the gate is unchanged.
- **Instrumentation is not in `ci`:** emulators are slow and billable. Unit tests gate PRs.
- **Release may debug-sign** when secrets are missing so forks still build.

---

## 19. Final Recommendations

### Option A — Minimal

```text
GitHub Actions → ./gradlew test → ./gradlew assembleDebug
```

For learning projects, small apps, prototypes.

### Option B — Standard

```text
GitHub Actions → ./gradlew ci → Quality → Unit Tests → Build → Artifact
```

For production apps, medium teams, multi-module projects (add modules; keep one `ci`).

### Option C — Scalable Platform

```text
Multiple Android Repositories
        │
        ▼
Reusable GitHub Workflows
        │
        ▼
Shared CI Standards
        │
        ▼
Quality Gates
        │
        ▼
Build / Test / Artifact
        │
        ▼
Distribution
```

For multiple apps, platform teams, large orgs.

### Adoption path

```text
Start with Gradle.
        ↓
Understand Tasks.
        ↓
Create Custom Tasks.
        ↓
Build a Local CI Command.
        ↓
Move the Same Command to GitHub Actions.
        ↓
Add Quality Gates.
        ↓
Add Artifacts.
        ↓
Add PR Validation.
        ↓
Add Secrets and Signing.
        ↓
Add Distribution.
        ↓
Extract Reusable Workflows.
        ↓
Integrate Across Multiple Android Projects.
```

**Recommended default for this lab and for most product teams: Option B**, then extract Option C when a second Android repository needs the same JDK/cache/quality bar.

---

## License

Use this repository as a personal or team learning lab. Do not commit secrets.
