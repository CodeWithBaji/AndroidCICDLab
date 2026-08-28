# Solution

Quality lives in `androidcicdlab.quality`:

- `detekt` + `ktlintCheck` on `:app`
- `ci` depends on both
- `detektFailureLab` analyzes only the violation file
- `-Plab.failQuality=true` includes that file in the default tasks

Config: `config/detekt/detekt.yml`  
Style: `.editorconfig` (ktlint)

## Where checks belong

| Check | Local | CI |
| --- | --- | --- |
| ktlintFormat (auto-fix) | IDE / pre-commit | Usually **not** — CI should not rewrite PRs silently |
| ktlintCheck | Before push | Required |
| detekt | Before push | Required, early |
| Android lint (not wired here) | Optional locally | Often CI-only; slower |

Quality should run **before** tests and packaging. A style violation is cheaper than a 4-minute APK.

## When strict gates become counterproductive

- Rules that fight Android/Compose idioms (`FunctionNaming` without `@Composable` ignore)
- Auto-correct in CI that fights the author's formatter
- Blocking PRs on thousands of baseline issues with no burn-down plan

Use a Detekt baseline for legacy code; do not start a greenfield lab with a baseline of lies.
