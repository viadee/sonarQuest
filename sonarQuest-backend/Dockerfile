LABEL org.opencontainers.image.source=https://github.com/viadee/sonarQuest
FROM openjdk:11-jre
RUN addgroup --gid 1000 appuser && \
    adduser --system --disabled-password --uid 1000 --ingroup appuser appuser
USER appuser
WORKDIR /home/appuser
COPY ./target/sonarQuest.jar app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
