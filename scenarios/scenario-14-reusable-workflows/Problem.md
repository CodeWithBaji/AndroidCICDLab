# Problem

Five Android repos copy-paste `ci.yml`. One updates JDK, four do not. The platform team has no single place to fix a cache bug.

## Context

`workflow_call` lets a workflow be a function. Inputs are the function parameters. Build logic still lives in each repo's Gradle (`ci` task).

## What to break

Point a caller at `@main` of a repo that then changes `gradle_task` default to a non-existent task. Every consumer goes red. That is the cost of unpinned `main`.
