#!/bin/bash

specializationjar=../build/libs/analyze-java-code-1.0-SNAPSHOT.jar
JAVA_HOME=$HOME/git-oracle/graal/sdk/latest_graalvm_home

classpath="bin:gen/bin/unnamed:$specializationjar"

$JAVA_HOME/bin/java --patch-module java.base=gen/bin/java.base --add-reads java.base=ALL-UNNAMED -Xbootclasspath/a:$classpath -cp $classpath my.testpackage.TestingSpecializedDataStructures
