# Solution

Reusable workflow: checkout, JDK, Gradle cache, optional extra quality, `./gradlew ${gradle_task}`, optional APK upload.

Consumers pass `gradle_task: ci` once they have a `ci` task (Level 3 in [integration-guide.md](../../docs/integration-guide.md)).

Pin with a tag or SHA. Use `secrets: inherit` only when the reusable workflow needs secrets (this one does not).

## Recommendation

Extract reusable workflows **after** `./gradlew ci` exists in two or more repos. Not before.
