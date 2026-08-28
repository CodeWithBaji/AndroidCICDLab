# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| Fail the build | Actually enforced | Can block urgent hotfixes |
| Warnings only | Low friction | Zero enforcement |
| Pre-commit hooks | Fast local loop | Easy to `--no-verify` |
| CI only | Consistent environment | Slow feedback |

## Final recommendation

Run ktlint + detekt locally and in CI. Keep `ci` dependent on them. Offer `detektFailureLab` for teaching. Never leave `main` red.
