# Problem

Without required checks, a red CI run is informational. Merge still happens. Branch protection is a **repository setting**, not something YAML can ethically auto-apply in a teaching repo.

## Context

`ci.yml` also runs on pull_request. That duplicates work unless you disable one. This lab keeps both: `ci.yml` is the annotated pipeline; `pr-validation.yml` is the reusable-workflow check you would require.

## What to break

Open a PR that fails `detektFailureLab` on the branch (or enable fail flags). The PR shows a red check. Do not merge it.
