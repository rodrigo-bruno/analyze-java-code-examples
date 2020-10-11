#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

javaparser=/home/rbruno/.gradle/caches/modules-2/files-2.1/com.github.javaparser/javaparser-core/3.15.13/7a2282962310f9a0d36d22464ee9c8f079667f04/javaparser-core-3.15.13.jar
mainjar=build/libs/analyze-java-code-1.0-SNAPSHOT.jar
testpackage=org.graalvm.datastructure.tests

workdir="/tmp/org.graalvm.datastructure.specialization"
patched=$workdir/patched/java.base

$JAVA_HOME/bin/java --patch-module java.base=$patched -cp $javaparser:$mainjar $testpackage.ArrayListTest
$JAVA_HOME/bin/java --patch-module java.base=$patched -cp $javaparser:$mainjar $testpackage.HashMapTest
$JAVA_HOME/bin/java --patch-module java.base=$patched -cp $javaparser:$mainjar $testpackage.ConcurrentHashMapTest
