# Scenario 8 — GitHub Actions Basic CI

Workflow: `.github/workflows/ci.yml`

```text
Checkout → Setup JDK → Restore Gradle Cache → Quality → Unit Tests → Build APK → Upload Artifact
```

Triggers: push and pull_request to `main`.

This workflow is **explicit** (every stage is a step with comments). Scenario 14 shows the reusable version.

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
