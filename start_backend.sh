#!/bin/sh

# start the backend server
cd grow && ./gradlew clean shadowJar && java -jar build/libs/grow.jar server

