#!/usr/bin/env bash

set -e 

# Requires pip installs of: mkdocs-bootswatch

./gradlew dokka
pushd docs && python3 -m mkdocs build && popd
