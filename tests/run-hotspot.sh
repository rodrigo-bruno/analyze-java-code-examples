#!/bin/bash

specializationjar=../build/libs/specialized-java-datastructures-1.0-SNAPSHOT.jar
JAVA_HOME=$HOME/software/labsjdk-ce-11.0.8-jvmci-20.2-b03/

classpath="bin:gen/bin/unnamed:$specializationjar"

specopts="--patch-module java.base=gen/bin/java.base --add-reads java.base=ALL-UNNAMED -Xbootclasspath/a:$classpath"
jvmopts="-XX:+UseSerialGC -Xmx256m -Xms256m"

# Build the application
$JAVA_HOME/bin/javac -cp $classpath --add-reads java.base=ALL-UNNAMED --patch-module java.base=gen/bin/java.base -d bin src/my/testpackage/*.java

function pubsub {
	# Running PopSub (vanilla)
	$JAVA_HOME/bin/java $jvmopts -Xloggc:popsub-vanilla.jvm -cp bin:$specializationjar my.testpackage.PopSubTest &> popsub-vanilla.log

	# Running PopSub (specialized)
	$JAVA_HOME/bin/java $jvmopts -Xloggc:popsub-specialized.jvm $specopts -cp $classpath my.testpackage.PopSubTest &> popsub-specialized.log

	# Running PopSub (specialized-user)
	$JAVA_HOME/bin/java $jvmopts -Xloggc:popsub-user-specialized.jvm  $specopts -cp $classpath my.testpackage.PopSubTestSpecialized &> popsub-user-specialized.log

	# Result processing for PopSub
	cat popsub-vanilla.log          | grep "took" | awk '{print $4}' > popsub-vanilla.dat
	cat popsub-specialized.log      | grep "took" | awk '{print $4}' > popsub-specialized.dat
	cat popsub-user-specialized.log | grep "took" | awk '{print $4}' > popsub-user-specialized.dat
}
pubsub

function arrayops {
	# Running ArrayRead (vanilla)
	$JAVA_HOME/bin/java $jvmopts -Xloggc:arrayread-vanilla.jvm -cp bin:$specializationjar my.testpackage.ArrayRead &> arrayread-vanilla.log

	# Running ArrayRead (specialized-user)
	$JAVA_HOME/bin/java $jvmopts -Xloggc:arrayread-user-specialized.jvm $specopts -cp bin:$specializationjar my.testpackage.ArrayReadSpecialized &> arrayread-user-specialized.log

	# Running ArrayWrite (vanilla)
	$JAVA_HOME/bin/java $jvmopts -Xloggc:arraywrite-vanilla.jvm -cp bin:$specializationjar my.testpackage.ArrayWrite &> arraywrite-vanilla.log

	# Running ArrayWrite (specialized-user)
	$JAVA_HOME/bin/java $jvmopts -Xloggc:arraywrite-user-specialized.jvm $specopts -cp bin:$specializationjar my.testpackage.ArrayWriteSpecialized &> arraywrite-user-specialized.log


	# Result processing for Array
	cat arrayread-vanilla.log           | grep "took" | awk '{print $4}' > arrayread-vanilla.dat
	cat arrayread-user-specialized.log  | grep "took" | awk '{print $4}' > arrayread-user-specialized.dat
	cat arraywrite-vanilla.log          | grep "took" | awk '{print $4}' > arraywrite-vanilla.dat
	cat arraywrite-user-specialized.log | grep "took" | awk '{print $4}' > arraywrite-user-specialized.dat
}
arrayops

function mapops {
	# Running MapRead (vanilla)
	$JAVA_HOME/bin/java $jvmopts -Xloggc:mapread-vanilla.jvm -cp bin:$specializationjar my.testpackage.MapRead &> mapread-vanilla.log

	# Running MapRead (specialized-user)
	$JAVA_HOME/bin/java $jvmopts -Xloggc:mapread-user-specialized.jvm $specopts -cp bin:$specializationjar my.testpackage.MapReadSpecialized &> mapread-user-specialized.log

	# Running MapWrite (vanilla)
	$JAVA_HOME/bin/java $jvmopts -Xloggc:mapwrite-vanilla.jvm -cp bin:$specializationjar my.testpackage.MapWrite &> mapwrite-vanilla.log

	# Running MapWrite (specialized-user)
	$JAVA_HOME/bin/java $jvmopts -Xloggc:mapwrite-user-specialized.jvm $specopts -cp bin:$specializationjar my.testpackage.MapWriteSpecialized &> mapwrite-user-specialized.log

	# Result processing for Map
	cat mapread-vanilla.log           | grep "took" | awk '{print $4}' > mapread-vanilla.dat
	cat mapread-user-specialized.log  | grep "took" | awk '{print $4}' > mapread-user-specialized.dat
	cat mapwrite-vanilla.log          | grep "took" | awk '{print $4}' > mapwrite-vanilla.dat
	cat mapwrite-user-specialized.log | grep "took" | awk '{print $4}' > mapwrite-user-specialized.dat

}
mapops
