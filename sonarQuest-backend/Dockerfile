FROM maven:3.5.3-jdk-8 as build_container
COPY  . /usr/src/sonarQuest
WORKDIR /usr/src/sonarQuest
RUN mvn clean package

FROM openjdk:8-jdk-alpine
RUN addgroup -g 1000 appuser && \
    adduser -D -u 1000 -G appuser appuser
USER appuser
WORKDIR /home/appuser
COPY --from=build_container /usr/src/sonarQuest/target/sonarQuest.jar app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
