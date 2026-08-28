# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| Reusable workflow | One JDK/cache standard | Cross-repo versioning |
| Copy-paste YAML | Independent | Drift |
| Pin `@main` | Always latest | Surprise breakage |
| Pin SHA | Reproducible | Manual upgrades |

## Final recommendation

Reusable workflows for **orchestration standards**. Gradle for **build logic**. Pin versions. Do not use `main` as the platform contract.
