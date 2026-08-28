# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| Debug-sign release in OSS | CI green on forks | Not store-uploadable |
| Required secrets on every PR | Always signed | Forks from outsiders cannot run |
| Org-level secrets | Shared apps | Broader blast radius |

## Final recommendation

Optional signing in public labs. Required signing in private production. Never commit key material.
