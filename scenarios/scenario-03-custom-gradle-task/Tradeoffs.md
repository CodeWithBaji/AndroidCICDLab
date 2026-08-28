# Trade-offs

| API | Use when | Do not use when |
| --- | --- | --- |
| `dependsOn` | The work is part of the definition of success | You only wanted ordering |
| `mustRunAfter` | Both tasks might run and order matters | The second task is the actual gate |
| `shouldRunAfter` | Soft order, possible cycles | CI correctness |
| `finalizedBy` | Cleanup/reporting after a task | Failing the pipeline |

## Final recommendation

Gates = `dependsOn`. Ordering = `mustRunAfter`. Reports = `finalizedBy`. Never encode the CI graph only in YAML.
