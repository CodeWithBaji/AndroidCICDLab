# Problem

If assemble succeeds but upload uses `app/build/outputs/apk/debug/*.apk`, the path is wrong once flavors exist. Failure E in scenario 9 is this class of bug.

## Context

APK ≠ AAB ≠ GitHub artifact ≠ GitHub Release ≠ Play listing.

## What to break

Change the upload path to `app/build/outputs/apk/debug/*.apk` and run CI. `if-no-files-found: error` should fail the job.
