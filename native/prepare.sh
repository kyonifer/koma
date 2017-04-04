#!/usr/bin/env bash

find ../golem-core -type f | xargs sed -i '/^@file/d'
find ../golem-core -type f | xargs sed -i '/^@JvmName/d'
find ../golem-core -type f | xargs sed -i 's/@JvmOverloads//g'
find ../golem-core -type f | xargs sed -i 's/@JvmField//g'
find ../golem-core -type f | xargs sed -i '/String.format/d'
find ../golem-core -type f | xargs sed -i '/import java.util/d'
find ../golem-core -type f | xargs sed -i 's/ Math\.abs/Math.fabs/g'

rm -rf golem-core/src/golem/util/validation
rm golem-core/src/golem/plot.kt
