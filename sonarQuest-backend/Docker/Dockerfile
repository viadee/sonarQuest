FROM maven:3.5.3-jdk-8
COPY  . /usr/src/sonarQuest
WORKDIR /usr/src/sonarQuest
EXPOSE 8080
ENTRYPOINT ["mvn", "spring-boot:run"]
