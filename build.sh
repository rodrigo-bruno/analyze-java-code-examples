#!/bin/bash

#export JAVA_HOME=/home/rbruno/git/labs-openjdk-11/java_home
export JAVA_HOME=/home/rbruno/software-oracle/oraclejdk1.8.0_261-jvmci-20.2-b03
gradle build
gradle install
gradle publishToMavenLocal
