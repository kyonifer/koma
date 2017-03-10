#!/usr/bin/env bash
# You need to set $DOKKA_FATJAR and build with 'gradle shadowJar' before running this.
java -jar $DOKKA_FATJAR -classpath build/libs/golem-all.jar -include golem-core/src/Module.md -format javadoc golem-*/src
