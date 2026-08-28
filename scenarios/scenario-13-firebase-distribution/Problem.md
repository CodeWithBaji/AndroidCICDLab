# Problem

Putting Firebase upload in the same job as unit tests couples "code is correct" to "testers got a build". A Firebase outage then blocks merge. That mixes CI and CD.

## Context

QA uses `qaDebug` (sideloadable, separate applicationId). Production uses AAB + Play.

## What to break

Point Firebase at `prodRelease` unsigned APKs. Testers install the wrong channel and cannot run side-by-side with their daily `dev` build.
