#!/usr/bin/env bash
./gradlew dokka
pushd docs && mkdocs build && popd
