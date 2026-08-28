# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| Flags on `main` | Easy | Someone will leave them true |
| Dedicated branches | Realistic | Branch rot |
| `workflow_dispatch` | Safe, repeatable | Only runs when you remember |

## Final recommendation

Flags default false + dispatch workflow. Document log signatures. That is how you train for on-call CI.
