# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| Flavors | Side-by-side installs, different resources | Task names explode; CI must pick one |
| Build types only | Simple | QA and prod share applicationId |
| Full matrix on every PR | High confidence | Slow and expensive |

## Final recommendation

Three flavors is enough for this lab and for most product apps. Do not CI the full matrix on every commit.
