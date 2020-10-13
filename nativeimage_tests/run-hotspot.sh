#!/bin/bash

JAVA_HOME=$HOME/git-oracle/graal/sdk/latest_graalvm_home

$JAVA_HOME/bin/java --patch-module java.base=gen/bin/java.base -Xbootclasspath/a:bin --add-reads java.base=ALL-UNNAMED -cp bin my.testpackage.IntegerArrayList
$JAVA_HOME/bin/java --patch-module java.base=gen/bin/java.base -Xbootclasspath/a:bin --add-reads java.base=ALL-UNNAMED -cp bin my.testpackage.PointArrayList
