# Solution

After `assembleDevDebug`, upload:

```yaml
- uses: actions/upload-artifact@v4
  with:
    name: debug-apk
    path: app/build/outputs/apk/dev/debug/*.apk
    if-no-files-found: error
```

`printArtifactPaths` documents the other variant directories.

Retention: 14 days on CI, 30 days on release AAB — enough to debug, not a substitute for Play.

## Recommendation

Always `if-no-files-found: error` for required artifacts. Use `warn` only for optional reports.
