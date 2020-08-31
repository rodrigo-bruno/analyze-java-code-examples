#!/bin/bash

export JAVA_HOME=/home/rbruno/software/openjdk1.8.0_252-jvmci-20.2-b01
./gradlew run | tee run.log
