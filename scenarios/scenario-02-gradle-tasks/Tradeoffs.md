# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| Many small tasks | Clear graph, `--dry-run` is readable | More names to learn |
| One giant `doLast` | Easy to write | No up-to-date checks, no parallelism |
| `tasks.register` vs `tasks.create` | Lazy, faster config | Slightly more verbose |

## Final recommendation

Register tasks lazily. Put discovery metadata on every task. Keep `projectInfo` out of `ci` — it is a teaching/diagnostic tool, not a gate.
