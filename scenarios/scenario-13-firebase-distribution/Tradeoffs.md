# Trade-offs

| Choice | Upside | Downside |
| --- | --- | --- |
| Firebase | Fast tester loop | Another secret, another vendor |
| Play internal testing | Closer to production | Slower review/track |
| Slack + GitHub APK | Zero extra product | Manual, no tester groups |

## Final recommendation

CI never depends on Firebase. QA flavor + optional CD job is enough for most teams.
