#!/usr/bin/env bash
cinterop -def ./Math.def -o Math.kt.bc
cinterop -def ./C.def -o C.kt.bc
kotlinc-native golem-core/src golem-core/srcnative main.kt -library Math.kt.bc -library C.kt.bc
