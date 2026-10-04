#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
test_classes=$(mktemp -d)
trap 'rm -rf "$test_classes"' EXIT
javac -encoding UTF-8 --release 8 -d "$test_classes" src/calc/*.java tests/calc/*.java
java -cp "$test_classes" calc.DiceProbityTest
