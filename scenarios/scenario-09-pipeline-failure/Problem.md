# Problem

Engineers who only see green pipelines cannot debug red ones. The skill is mapping a log signature to a root cause.

## Failure signatures

| Lab | Where it fails | Log hint |
| --- | --- | --- |
| A | Gradle test task | `Failure lab: lab.failTests=true` / `There were failing tests` |
| B | Detekt | issues in `QualityViolationLab.kt` |
| C | Configuration | `lab.failBuild=true` during configuration (before most tasks) |
| D | Shell step | `KEYSTORE_PASSWORD is not defined` |
| E | upload-artifact | `No files were found with the provided path` |

## What to break

Do **not** push `-Plab.failTests=true` on `main`. Use a feature branch or `failure-lab.yml`.
