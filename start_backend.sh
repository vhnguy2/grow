#!/bin/sh

# start the backend server
#cd grow && ./gradlew clean shadowJar && java -jar build/libs/grow.jar server

# build Docker container and run if successful
docker build -t java-react-grow . && docker run -d -p 8080:8080 --name grow-backend java-react-grow

