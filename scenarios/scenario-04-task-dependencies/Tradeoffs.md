# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| Wrapper tasks (`runTests`) | Teachable graph, isolatable stages | Extra names on `./gradlew tasks` |
| Only `ci` with four dependsOn | Simpler | Harder to run "just quality" with the same ordering |
| `mustRunAfter` without `dependsOn` | Flexible | Stages can be skipped accidentally |

## Final recommendation

Keep both: `ci` for "do the work", `pipeline` for "see the graph". Production apps can ship only `ci`.
