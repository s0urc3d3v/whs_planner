#!/usr/bin/env bash
mvn clean compile assembly:single -DskipTests
mvn package appbundle:bundle -DskipTests
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
