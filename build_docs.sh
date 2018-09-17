#!/usr/bin/env bash

# Requires pip installs of: mkdocs-bootswatch, mkdocs, pygments

set -e

./gradlew dokka
pushd docs && python3 -m mkdocs build && popd
pygmentize -S default -f html -a .codehilite > docs/site/code_styles.css

# Remote deploy:
#
# ./gradlew dokka
# pushd docs && python3 -m mkdocs gh-deploy && popd
# git checkout gh-pages
# echo "koma.kyonifer.com" > CNAME
# pygmentize -S default -f html -a .codehilite > code_styles.css
# git add CNAME code_styles.css && git commit --amend
# git push origin gh-pages --force-with-lease
