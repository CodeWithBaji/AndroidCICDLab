# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| GitHub artifact | Simple, per-run | Not a distribution channel; retention limits |
| Commit APKs to git | Never | Repo size, unsigned/wrong variants |
| AAB on every PR | Closer to store | Slower; usually unnecessary |

## Final recommendation

PR/CI: APK artifact. Release: AAB artifact. Testers: Firebase. Customers: Play.
