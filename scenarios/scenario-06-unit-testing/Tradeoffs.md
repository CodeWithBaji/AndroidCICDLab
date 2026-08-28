# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| JVM unit tests | Fast, no emulator | Cannot assert real Compose rendering |
| Compose instrumentation | Real UI | Slow, flaky without discipline |
| MockK | Isolates collaborators | Over-mocking tests the mock |

## Final recommendation

Gate PRs on JVM tests. Keep one or two instrumentation tests as a smoke check, run them where you have emulators. Never ignore failures.
