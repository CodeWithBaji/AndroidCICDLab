#!/usr/bin/env bash
# Verifies that a machine can run the AndroidCICDLab Gradle pipeline.
set -euo pipefail

echo "== AndroidCICDLab environment =="

if ! command -v java >/dev/null 2>&1; then
  echo "ERROR: java is not on PATH. Install JDK 17+."
  exit 1
fi
echo "java: $(java -version 2>&1 | head -n 1)"

if [[ -z "${JAVA_HOME:-}" ]]; then
  echo "WARN: JAVA_HOME is not set. Gradle will use the java on PATH."
else
  echo "JAVA_HOME: ${JAVA_HOME}"
fi

if [[ -z "${ANDROID_HOME:-}" && -z "${ANDROID_SDK_ROOT:-}" ]]; then
  echo "WARN: ANDROID_HOME / ANDROID_SDK_ROOT is not set."
  echo "      SDK-dependent tasks (connectedAndroidTest) will fail."
else
  echo "ANDROID_HOME: ${ANDROID_HOME:-${ANDROID_SDK_ROOT}}"
fi

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
if [[ ! -x "${ROOT}/gradlew" ]]; then
  echo "ERROR: gradlew is missing or not executable at ${ROOT}/gradlew"
  exit 1
fi
echo "gradlew: ${ROOT}/gradlew"

echo
echo "Try:"
echo "  ./gradlew projectInfo"
echo "  ./gradlew ci"
echo "== OK =="
