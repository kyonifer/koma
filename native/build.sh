#!/usr/bin/env bash
cinterop -def ./Math.def -o Math.kt.bc
kotlinc-native golem-core/src golem-core/srcnative main.kt -library Math.kt.bc 
