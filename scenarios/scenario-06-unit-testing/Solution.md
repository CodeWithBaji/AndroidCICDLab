# Solution

`testOptions.unitTests` copies `lab.failTests` into a JVM system property. `FailureLabTest` fails only then.

Reports:

| Format | Path |
| --- | --- |
| HTML | `app/build/reports/tests/testDevDebugUnitTest/index.html` |
| XML (CI) | `app/build/test-results/testDevDebugUnitTest/` |

`ci` depends on `testDevDebugUnitTest`. A failed assertion → non-zero Gradle → failed GitHub job.

Do not set `ignoreFailures` on CI test tasks.

## Recommendation

Unit tests on every PR. Instrumentation on a device farm or a nightly job — they need emulators and are slower.
