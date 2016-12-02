#!/usr/bin/env bash

mvn install
mvn compiler:compile
mvn org.apache.maven.plugins:maven-compiler-plugin:compile
mvn org.apache.maven.plugins:maven-compiler-plugin:2.0.2:compile
