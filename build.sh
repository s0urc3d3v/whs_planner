#!/usr/bin/env bash
./clean.sh
mvn clean compile assembly:single -DskipTests
mvn package -DskipTests
mvn package appbundle:bundle -DskipTests
