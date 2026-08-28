# Solution

Flavor dimension `environment`:

| Flavor | Suffix | Typical job |
| --- | --- | --- |
| dev | `.dev` | Local + default CI |
| qa | `.qa` | Firebase / QA |
| prod | none | Play / release |

The home screen shows `BuildConfig.FLAVOR` + `BUILD_TYPE`.

CI pins `devDebug`. Distribution jobs pin `qaDebug`. Store jobs pin `prodRelease` / `bundleProdRelease`.

## Recommendation

One flavor dimension until you have a real second axis (paid/free, company/brand). Each extra dimension multiplies variants.
