#!/usr/bin/env bash

find ../golem-core ../golem-backend-purekt -type f | xargs sed -i 's/ Math\./ kotlin.js.Math./g'
find ../golem-core -type f | xargs sed -i '/String.format/d'

rm golem-core/src/golem/plot.kt
