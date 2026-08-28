# Scenario 11 — Pull Request Validation

Workflow: `.github/workflows/pr-validation.yml`  
It **calls** the reusable workflow so PR status matches other labs.

```text
Pull Request → Checkout → Code Quality → Unit Tests → Build → PR Status
```

```text
PR
│
├── 🟢 CI Passed
│
or
│
└── 🔴 CI Failed
```

## Files

- [Problem.md](Problem.md)
- [Solution.md](Solution.md)
- [Tradeoffs.md](Tradeoffs.md)
