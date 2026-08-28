# Solution

`ci.yml` uses official actions only:

- `actions/checkout@v4`
- `actions/setup-java@v4` (Temurin 17)
- `gradle/actions/setup-gradle@v4`
- `actions/upload-artifact@v4` (`debug-apk`)

Each stage is a separate step so a failure is obvious in the UI. Gradle still owns detekt, tests, and assemble.

Concurrency cancels outdated runs on the same ref.

## Recommendation

Keep this file as the "readable" pipeline. Production repos may collapse it to `./gradlew ci` plus upload (or the reusable workflow).
