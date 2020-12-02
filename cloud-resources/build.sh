#!/bin/sh

home=$(pwd) &&
mvn clean &&
mvn clean compile assembly:single &&
mv target/cloud-resources-0.0.1-SNAPSHOT-jar-with-dependencies.jar $home/cloud-resource-0.0.1.jar
