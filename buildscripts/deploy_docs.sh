#!/usr/bin/env bash

pushd ..
./gradlew dokka
pushd docs && python3 -m mkdocs gh-deploy && popd
git checkout gh-pages
echo "koma.kyonifer.com" > CNAME
pygmentize -S default -f html -a .codehilite > code_styles.css
git add CNAME code_styles.css && git commit --amend
git push origin gh-pages --force-with-lease
popd