#!/bin/sh

home=$(pwd) &&
mvn clean &&
mvn clean compile assembly:single &&
mv target/aws-resources-0.0.1-SNAPSHOT-jar-with-dependencies.jar $home/aws-resource-0.0.1.jar
