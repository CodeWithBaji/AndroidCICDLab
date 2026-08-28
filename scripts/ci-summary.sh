#!/usr/bin/env bash
# Prints where Gradle writes reports and artifacts after a local CI run.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

echo "== AndroidCICDLab CI summary =="
echo "Root: ${ROOT}"
echo
echo "Quality"
echo "  Detekt HTML:  ${ROOT}/app/build/reports/detekt/detekt.html"
echo "  Ktlint:       ${ROOT}/app/build/reports/ktlint/"
echo
echo "Tests"
echo "  Unit HTML:    ${ROOT}/app/build/reports/tests/testDevDebugUnitTest/index.html"
echo "  Unit XML:     ${ROOT}/app/build/test-results/testDevDebugUnitTest/"
echo
echo "Artifacts"
echo "  APK devDebug: ${ROOT}/app/build/outputs/apk/dev/debug/"
echo "  AAB prodRel:  ${ROOT}/app/build/outputs/bundle/prodRelease/"
echo
echo "Pipeline report"
echo "  ${ROOT}/pipeline-report.txt"
echo
echo "Gradle exit code of the last command is what GitHub Actions uses"
echo "to mark the job as success (0) or failure (non-zero)."
