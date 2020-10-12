#!/bin/bash

JAVA_HOME=$HOME/git-oracle/graal/sdk/latest_graalvm_home

patched=gen/bin/java.base

$JAVA_HOME/bin/java --patch-module java.base=$patched -Xbootclasspath/a:bin --add-reads java.base=ALL-UNNAMED -cp bin my.testpackage.PointArrayList
