#!/usr/bin/env bash

find ../golem-core -type f | xargs sed -i '/^@file/d'
find ../golem-core -type f | xargs sed -i '/^@JvmName/d'
find ../golem-core -type f | xargs sed -i 's/@JvmOverloads//g'
find ../golem-core -type f | xargs sed -i 's/@JvmField//g'
find ../golem-core ../golem-backend-purekt -type f | xargs sed -i 's/ Math\./ kotlin.js.Math./g'
find ../golem-core -type f | xargs sed -i '/String.format/d'
find ../golem-core ../golem-backend-purekt -type f | xargs sed -i '/fun T()/d'
find ../golem-core -type f | xargs sed -i '/import java.util/d'





rm golem-core/src/golem/plot.kt
