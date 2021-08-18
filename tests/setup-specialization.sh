#!/bin/bash

specializationjar=../build/libs/specialized-java-datastructures-1.0-SNAPSHOT.jar
javaparser=$HOME/.m2/repository/com/github/javaparser/javaparser-core/3.15.13/javaparser-core-3.15.13.jar
jdksources=$HOME/git/labs-openjdk-11/src/java.base/share/classes

# Compile Point as we need it for specialization.
javac -d bin src/my/testpackage/Point.java

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
	"java.util.HashMap<Integer, my.testpackage.Point>" \
	"java.util.concurrent.ConcurrentHashMap<Integer,String>" \
	"java.util.concurrent.ConcurrentHashMap<String,my.testpackage.Point>"
