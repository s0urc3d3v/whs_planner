call mvn clean compile assembly:single -DskipTests
call mvn package -DskipTests

@echo off
REM For this script to work your JAVA_HOME needs to be set and point the JDK dir not the jdk/bin dir | also use call becuase mvn is batch file so windows gets confused... windows.....
REM Comments in bash are MLG
@echo on

set DIR=%cd%
%DIR%\target\whs_planner_app-1.0-SNAPSHOT-jar-with-dependencies.jar

