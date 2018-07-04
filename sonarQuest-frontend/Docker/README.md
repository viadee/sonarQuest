# Dockerfiles

``Dockerfile_nginx`` creates an image containing an nginx serving the compiled application.

Build with

```
docker build -t sonarquest-frontend-nginx -f Docker/Dockerfile_nginx .
```

and run with

```
docker run -it -p 4200:80 -d sonarquest-frontend-nginx
```
