FROM openjdk:11-jdk
RUN mkdir /srv/docker-server
WORKDIR /srv/docker-server
COPY ./ ./
CMD chmod +x ./gradlew && ./gradlew bootJar && java -jar ./build/libs/*.jar
