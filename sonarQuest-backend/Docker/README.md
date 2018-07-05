# Dockerfiles

Dockerfile2 is an alternative to the `Dockerfile` which is
used with docker-compose.

It allows an external configuration via an `application.properties`
lying on the docker host and which is made available to the
docker container via volumes.

Build the image e.g. from within the backend folder with

```
$ docker build -t sonarquest-backend -f Docker/Dockerfile2 .
```

The image expects the configuration files to be in folder `/root/conf/`.

Example setup with an external h2 file database:

let `sqb` be the current directory.
Define an `application.properties` in folder `sqb` with content

```
spring.datasource.url=jdbc:h2:file:/tmp/sonarQuest/sonarQuest
```

Start the container with

```
docker run -v `pwd`:/root/conf -v /tmp/sonarQuestDb:/tmp/sonarQuest sonarquest-backend
```

The h2 database file will then be created (or used) in the host's folder
`/tmp/sonarQuestDb`.


