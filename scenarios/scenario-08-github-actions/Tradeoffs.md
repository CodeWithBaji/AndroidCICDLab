# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| One step `./gradlew ci` | DRY with local | Harder to see which stage failed in the UI |
| Many Gradle steps | Clear UI | YAML duplicates the graph; can drift |
| macos runners | Slightly closer to some laptops | Slower and more expensive than ubuntu |

## Final recommendation

Teach with staged YAML (`ci.yml`). Operate with `./gradlew ci` (this file could be collapsed later). Ubuntu + JDK 17 + Gradle cache.
