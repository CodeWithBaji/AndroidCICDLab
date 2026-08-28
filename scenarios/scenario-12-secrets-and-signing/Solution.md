# Solution

1. Local: gitignored properties file + keystore path.
2. CI: `KEYSTORE_BASE64` decoded to `upload-keystore.jks`, properties written from env.
3. Placeholders only in git.

Environment variables vs secrets: secrets are stored encrypted by GitHub and injected at runtime. Env vars on the runner are the decoded form. Do not `echo` them.

## Recommendation

Upload key ≠ debug key. Rotate on leak. Pin release jobs to `prodRelease` / AAB.
