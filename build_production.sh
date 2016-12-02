#!/usr/bin/env bash

mvn install -DskipTests
mvn compiler:compile -DskipTests
mvn org.apache.maven.plugins:maven-compiler-plugin:compile -DskipTests
mvn org.apache.maven.plugins:maven-compiler-plugin:2.0.2:compile -DskipTests
mvn package -DskipTests