FROM maven:3.6.3-jdk-11 as build_container
COPY  . /usr/src/sonarQuest
WORKDIR /usr/src/sonarQuest
RUN mvn clean package
# Alpine ist für Version 11 nicht verfügbar
FROM openjdk:11-jdk
RUN addgroup --gid 1000 appuser && \
    adduser --system --disabled-password --uid 1000 --ingroup appuser appuser
USER appuser
WORKDIR /home/appuser
COPY --from=build_container /usr/src/sonarQuest/target/sonarQuest.jar app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]