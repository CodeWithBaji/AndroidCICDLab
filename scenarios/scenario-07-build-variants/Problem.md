# Problem

One applicationId, one backend, one signing config — until QA needs a side-by-side install and production needs a Play AAB. Without flavors, teams copy entire modules or ship `if (BuildConfig.DEBUG)` to production.

## Context

See [docs/build-variants.md](../../docs/build-variants.md).

## What to break

Install `devDebug` and `qaDebug` on one device. They coexist because of `applicationIdSuffix`. Install two `prod` debug builds and they replace each other.
