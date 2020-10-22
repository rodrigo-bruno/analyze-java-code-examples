#!/bin/bash

specializationjar=../build/libs/analyze-java-code-1.0-SNAPSHOT.jar
JAVA_HOME=$HOME/git-oracle/graal/sdk/latest_graalvm_home

classpath="bin:gen/bin/unnamed:$specializationjar"

specializationflags="-J--patch-module -Jjava.base=gen/bin/java.base -J-Xbootclasspath/a:$classpath -J--add-reads -Jjava.base=ALL-UNNAMED"

$JAVA_HOME/bin/native-image $specializationflags -cp $classpath my.testpackage.TestingSpecializedDataStructures
./my.testpackage.testingspecializeddatastructures


#$JAVA_HOME/bin/native-image $specializationflags -H:ConfigurationFileDirectories=./config -cp bin my.testpackage.PointArrayList
#./my.testpackage.PointArrayList
