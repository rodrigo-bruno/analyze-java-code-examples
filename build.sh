#!/bin/bash

gradle build
gradle install
gradle publishToMavenLocal
