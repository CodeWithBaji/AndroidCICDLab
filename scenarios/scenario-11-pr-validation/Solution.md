# Solution

`pr-validation.yml`:

```yaml
jobs:
  validate:
    uses: ./.github/workflows/reusable-android-ci.yml
    with:
      gradle_task: ci
      build_variant: DevDebug
```

## Manual GitHub configuration

These steps are **not** automated by this repository:

1. Open the GitHub repo → **Settings** → **Branches**.
2. Add a protection rule for `main`.
3. Enable **Require a pull request before merging**.
4. Enable **Require status checks to pass before merging**.
5. Search and select the check named like `PR validation / Android CI (DevDebug)`.
6. Optionally require `Quality, test, assemble` from `ci.yml` if you keep both workflows.
7. Restrict who can push to `main`.

Until those boxes are checked, a green or red check is only a signal.

## Recommendation

Require one workflow on `main`. Delete the duplicate trigger if the bill or queue hurts.
