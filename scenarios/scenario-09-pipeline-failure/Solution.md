# Solution

### A — Unit test

Root cause: assertion in `FailureLabTest`.  
Fix: omit `-Plab.failTests` or set `lab.failTests=false` in `gradle.properties`.

### B — Detekt

Root cause: intentional violations.  
Fix: run default `detekt` (file excluded) or delete/fix the lab file.

### C — Build

Root cause: convention plugin `error()` when property is true.  
Fix: do not pass `-Plab.failBuild=true`. Note: this fails **configuration**, so `--dry-run` also fails.

### D — Missing secret

Root cause: secret never created in the repo. GitHub injects empty env.  
Fix: Settings → Secrets. Do not echo secret values.

### E — Missing artifact

Root cause: upload path does not match Gradle output (wrong flavor or assemble skipped).  
Fix: upload `app/build/outputs/apk/dev/debug/*.apk` after `assembleDevDebug`.

GitHub logs: first red step wins. Expand it. Gradle’s “What went wrong” is the task name.

## Recommendation

Keep a dispatch-only failure workflow. Never merge a red `main`.
