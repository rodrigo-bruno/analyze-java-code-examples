#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
gradle run | tee run.log
