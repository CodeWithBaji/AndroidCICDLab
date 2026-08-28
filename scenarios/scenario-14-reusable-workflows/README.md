# Scenario 14 — Reusable GitHub Actions Workflows

File: `.github/workflows/reusable-android-ci.yml`  
Caller: `.github/workflows/pr-validation.yml`

```text
AndroidScenarioLab ──┐
ComposeScenarioLab ──┼──► Reusable Android CI Workflow
CoroutineScenarioLab─┘
```

Inputs: `gradle_task`, `build_variant`, `run_quality_checks`.

See [docs/reusable-workflows.md](../../docs/reusable-workflows.md).

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
