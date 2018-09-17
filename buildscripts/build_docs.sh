#!/usr/bin/env bash

# Requires pip installs of: mkdocs-bootswatch, mkdocs, pygments

set -e

pushd ..
./gradlew dokka
pushd docs && python3 -m mkdocs build && popd
pygmentize -S default -f html -a .codehilite > docs/site/code_styles.css
popd
