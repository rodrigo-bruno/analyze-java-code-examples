#!/bin/bash

specializationjar=../build/libs/analyze-java-code-1.0-SNAPSHOT.jar
javaparser=/home/rbruno/.m2/repository/com/github/javaparser/javaparser-core/3.15.13/javaparser-core-3.15.13.jar
jdksources=/home/rbruno/git/labs-openjdk-11/src/java.base/share/classes

# Compile application.
javac -cp $specializationjar -d bin src/my/testpackage/*.java

# Create (.java) and compile (.class) specialized data structures.
java \
	-cp $javaparser:$specializationjar:bin \
	org.graalvm.datastructure.specialization.TypeSpecialization \
	$jdksources \
	gen/src \
	gen/bin \
	"java.util.ArrayList<Integer>" \
	"java.util.ArrayList<my.testpackage.Point>" \
	"java.util.HashMap<Integer, String>" \
	"java.util.HashMap<String, my.testpackage.Point>" \
	"java.util.concurrent.ConcurrentHashMap<Integer,String>" \
	"java.util.concurrent.ConcurrentHashMap<String,my.testpackage.Point>"
