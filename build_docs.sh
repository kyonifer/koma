#!/usr/bin/env bash

# Requires pip installs of: mkdocs-bootswatch

set -e

./gradlew dokka
pushd docs && python3 -m mkdocs build && popd

# Remote deploy:
# pushd docs && python3 -m mkdocs gh-deploy && popd
# git checkout gh-pages
# echo "koma.kyonifer.com" > CNAME
# git add CNAME && git commit --amend
# git push origin gh-pages --force-with-lease