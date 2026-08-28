# Problem

If quality tools are "advisory", they are ignored. If they are maximally strict on day one, the team disables them. The engineering problem is **when** they run and **how strict** they are.

## Context

`QualityViolationLab.kt` contains wildcard imports, magic numbers, unused properties, and too many functions. Default Detekt/Ktlint **exclude** that file so `main` stays shippable.

## What to break

```bash
./gradlew detektFailureLab
```

Read the HTML report. Then fix nothing — restore green by running plain `./gradlew detekt` again.
