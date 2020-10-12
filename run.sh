#!/bin/bash

source run.env

mainClass=org.graalvm.datastructure.specialization.TypeSpecialization

java -cp $javaparser:$specializationjar $mainClass $jdksources $src_java_base $bin_java_base
