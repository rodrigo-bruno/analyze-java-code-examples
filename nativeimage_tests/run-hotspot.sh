#!/bin/bash

specializationjar=../build/libs/analyze-java-code-1.0-SNAPSHOT.jar
#JAVA_HOME=$HOME/git-oracle/graal/sdk/latest_graalvm_home
JAVA_HOME=$HOME/software/labsjdk-ce-11.0.8-jvmci-20.2-b03/

classpath="bin:gen/bin/unnamed:$specializationjar"

specopts="--patch-module java.base=gen/bin/java.base --add-reads java.base=ALL-UNNAMED -Xbootclasspath/a:$classpath"
jvmopts="-XX:+UseSerialGC -Xmx256m -Xms256m"

# Build the application
$JAVA_HOME/bin/javac -cp $classpath --add-reads java.base=ALL-UNNAMED --patch-module java.base=gen/bin/java.base -d bin src/my/testpackage/*.java

# Running TestingSpecializedDataStructures
#$JAVA_HOME/bin/java $specopts -cp $classpath my.testpackage.TestingSpecializedDataStructures

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
