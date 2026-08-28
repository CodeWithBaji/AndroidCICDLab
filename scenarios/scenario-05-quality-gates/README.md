# Scenario 5 — Quality Gates

Detekt and Ktlint fail the build when they find issues. Default `main` is green. Violations are opt-in so you can experiment.

## Try (green)

```bash
./gradlew detekt
./gradlew ktlintCheck
```

## Try (red, on purpose)

```bash
./gradlew detektFailureLab
./gradlew detekt ktlintCheck -Plab.failQuality=true
```

```text
Developer Push
      ↓
Quality Check
      ↓
Violation?
   ↙       ↘
 YES       NO
  ↓         ↓
FAIL      CONTINUE
```

Reports: `app/build/reports/detekt/detekt.html`

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
