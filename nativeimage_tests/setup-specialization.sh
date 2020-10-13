#!/bin/bash

specializationjar=../build/libs/analyze-java-code-1.0-SNAPSHOT.jar
javaparser=/home/rbruno/.gradle/caches/modules-2/files-2.1/com.github.javaparser/javaparser-core/3.15.13/7a2282962310f9a0d36d22464ee9c8f079667f04/javaparser-core-3.15.13.jar
jdksources=/home/rbruno/git/labs-openjdk-11/src/java.base/share/classes
src_java_base=gen/src/java.base
bin_java_base=gen/bin/java.base

# Compile necessary classes to run the specialization.
javac -cp $specializationjar -d bin src/my/testpackage/*.java

# Create (.java) and compile (.class) specialized data structures.
java -cp $javaparser:$specializationjar:bin my.testpackage.SetupSpecialization $jdksources $src_java_base $bin_java_base

# Compile specialized factory. 
# TODO - generate automatically
# If needed to update inside jar, use: jar -uf $optimized_jar -C gen/bin/unnamed com/oracle/DataStructureFactory.class 
javac -cp bin --add-reads java.base=ALL-UNNAMED --patch-module java.base=$bin_java_base -d bin DataStructureFactory.java
