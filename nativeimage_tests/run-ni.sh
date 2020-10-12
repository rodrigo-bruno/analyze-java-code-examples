#!/bin/bash

JAVA_HOME=$HOME/git-oracle/graal/sdk/latest_graalvm_home

patched=gen/bin/java.base

$JAVA_HOME/bin/native-image -J--patch-module -Jjava.base=$patched -J-Xbootclasspath/a:bin -J--add-reads -Jjava.base=ALL-UNNAMED -cp bin my.testpackage.PointArrayList
