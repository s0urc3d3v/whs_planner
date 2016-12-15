#!/usr/bin/env bash
mvn clean compile assembly:single -DskipTests
mvn package appbundle:bundle -DskipTests
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
printf "\n\n RUNNING JAR \n\n"
/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/bin/java -Dfile.encoding=UTF-8 -jar $DIR/target/whs_planner_app-1.0-SNAPSHOT-jar-with-dependencies.jar