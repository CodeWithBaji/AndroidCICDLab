# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| PR workflow + push workflow | Extra coverage on `main` | Double CI on PRs (push to a PR branch may not run `ci.yml` unless targeting main) |
| Required checks | Real quality bar | Admins must not bypass without audit |
| Optional checks | Faster merge | Useless gate |

Note: `ci.yml` runs on `pull_request` **and** `push` to main. A PR to main runs **both** `ci.yml` and `pr-validation.yml`. That is intentional duplication for teaching. Production: pick one.

## Final recommendation

One required check. Prefer the reusable workflow so other Scenario Labs match.
