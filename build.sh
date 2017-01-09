#!/usr/bin/env bash
./clean.sh
mvn clean compile assembly:single -DskipTests -X
mvn package -DskipTests -X
mvn package appbundle:bundle -DskipTests -X
