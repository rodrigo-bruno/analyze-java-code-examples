#!/bin/bash

JAVA_HOME=$HOME/git-oracle/graal/sdk/latest_graalvm_home

patched=gen/bin/java.base

specializationflags="-J--patch-module -Jjava.base=$patched -J-Xbootclasspath/a:bin -J--add-reads -Jjava.base=ALL-UNNAMED"

$JAVA_HOME/bin/native-image $specializationflags -cp bin my.testpackage.PointArrayList
./my.testpackage.pointarraylist

$JAVA_HOME/bin/native-image $specializationflags -cp bin my.testpackage.IntegerArrayList
./my.testpackage.integerarraylist

# TODO - add an example with a Hashmap so that we can actually use the config
#$JAVA_HOME/bin/native-image $specializationflags -H:ConfigurationFileDirectories=./config -cp bin my.testpackage.PointArrayList
#./my.testpackage.PointArrayList
