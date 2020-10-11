#!/bin/bash

JAVA_HOME=$HOME/git-oracle/graal/sdk/latest_graalvm_home

patched=/tmp/org.graalvm.datastructure.specialization/patched/java.base

$JAVA_HOME/bin/javac --patch-module java.base=$patched SpecializedArrayList.java
$JAVA_HOME/bin/native-image -J--patch-module -Jjava.base=$patched SpecializedArrayList
