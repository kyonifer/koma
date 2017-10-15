To build the docs:

1. Install mkdocs and mkdocs-bootswatch from pip
2. Run `./gradlew dokka` in the project root directory. This will populate the `docs/markdown/Reference_API_Docs` folder.
3. Run `mkdocs build` in this directory. This will populate the `docs/markdown/site` folder with html docs.