FROM openjdk:8-jdk-alpine
COPY . /var/www/java  
WORKDIR /var/www/java/grow  
RUN ./gradlew clean shadowJar
CMD ["java", "-jar", "build/libs/grow.jar", "server"]  
