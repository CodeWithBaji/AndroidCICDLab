# Problem

A keystore in git is a stolen Play upload key waiting to happen. Passing passwords as workflow `echo ${{ secrets.X }}` in the YAML file can also leak into logs if not using env vars.

## Context

Convention plugin reads `keystore.properties` if present and configures `signingConfigs.release`. GitHub `release.yml` reconstructs that file on the runner from secrets.

## What to break

Run Failure D in `failure-lab.yml` (`missing-secret`). Empty env means the secret was never set.
