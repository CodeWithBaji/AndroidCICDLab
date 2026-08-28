# Solution

- Default PR pipeline: `ci` / `devDebug` — **no** Firebase.
- `develop` or dispatch: `assembleQaDebug` then distribute.
- Missing secrets: skip CD, keep CI.

When Firebase **is** appropriate:

- Internal QA groups
- Weekly builds
- Devices that cannot use Play internal testing

When it is not:

- Public customers (use Play)
- Replacing automated tests
- Every commit on `main` (noise and quota)

## Recommendation

Keep Firebase out of `./gradlew ci`. CD workflows consume CI artifacts or rebuild one QA variant.
