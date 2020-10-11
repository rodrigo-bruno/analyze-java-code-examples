#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

javaparser=/home/rbruno/.gradle/caches/modules-2/files-2.1/com.github.javaparser/javaparser-core/3.15.13/7a2282962310f9a0d36d22464ee9c8f079667f04/javaparser-core-3.15.13.jar
mainClass=org.graalvm.datastructure.specialization.TypeSpecialization

jdksources="/home/rbruno/git/labs-openjdk-11/src/java.base/share/classes/"
workdir="/tmp/org.graalvm.datastructure.specialization"

# This will generate all data structure sources
$JAVA_HOME/bin/java -cp $javaparser:build/libs/analyze-java-code-1.0-SNAPSHOT.jar $mainClass $jdksources $workdir
