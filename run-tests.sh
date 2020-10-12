#!/bin/bash

source run.env

testpackage=org.graalvm.datastructure.tests

# Note: this mkdir is necessary otherwise the JVM will not load classes from
# this directory if it is not available at startup...
mkdir -p gen/bin/java.base

function run_test {
	test=$1
	java \
		-Djdksources=$jdksources \
		-Dsrc_java_base=$src_java_base \
		-Dbin_java_base=$bin_java_base \
		--patch-module java.base=$bin_java_base \
		-Xbootclasspath/a:$javaparser \
		-Xbootclasspath/a:build/classes/java/main \
		--add-reads java.base=ALL-UNNAMED \
		-cp $javaparser:build/classes/java/main $testpackage.$test
}

run_test ArrayListTest
run_test ArrayListTest2
run_test HashMapTest
run_test ConcurrentHashMapTest
