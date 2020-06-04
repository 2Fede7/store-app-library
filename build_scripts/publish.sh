#!/bin/bash -el

./gradlew clean build generatePomFileForMavenPublication artifactoryPublish --info