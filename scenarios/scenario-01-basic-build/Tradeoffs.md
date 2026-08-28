# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| `assembleDebug` in CI | Simple name | Builds every flavor; slower, more artifacts |
| `assembleDevDebug` in CI | Fast, one APK | Must document the variant name |
| Configuration cache | Faster CI after the first run | Custom tasks must not capture `Project` in `doLast` |
| Committing `app/build` | Never do this | Huge diffs, non-reproducible |

## Final recommendation

Teach the lifecycle with `--dry-run` and `printArtifactPaths`. Pin CI to one variant. Keep outputs out of git.
