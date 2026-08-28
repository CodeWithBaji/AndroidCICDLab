# Scenario 13 — Firebase App Distribution

```text
Merge to Develop → Build QA APK → Run Tests → Firebase App Distribution → QA Testers
```

```text
CI  = Build + Test + Verify
CD  = Distribute + Release
```

Workflow: `.github/workflows/firebase-distribution.yml`  
Secrets are placeholders (`FIREBASE_SERVICE_ACCOUNT`, `FIREBASE_APP_ID`, `FIREBASE_TESTER_GROUPS`). Without them the job **skips upload** and stays green.

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
