#!/bin/bash

specializationjar=../build/libs/analyze-java-code-1.0-SNAPSHOT.jar
JAVA_HOME=$HOME/git-oracle/graal/sdk/latest_graalvm_home

classpath="bin:gen/bin/unnamed:$specializationjar"

specializationflags="-J--patch-module -Jjava.base=gen/bin/java.base -J-Xbootclasspath/a:$classpath -J--add-reads -Jjava.base=ALL-UNNAMED"
dashboardflags="-H:+DashboardAll -H:+DashboardPretty"
oiflags="-H:Log=ObjectInlining:3 -H:ConfigurationFileDirectories=./config"

dashboard="-H:DashboardDump=/tmp/application-inlined.dump $dashboardflags"
$JAVA_HOME/bin/native-image $specializationflags $oiflags -cp $classpath my.testpackage.TestingSpecializedDataStructures specialized-inlined
./specialized-inlined

dashboard="-H:DashboardDump=/tmp/application-vanilla.dump $dashboardflags"
$JAVA_HOME/bin/native-image $specializationflags -cp $classpath my.testpackage.TestingSpecializedDataStructures specialized-vanilla
./specialized-vanilla
