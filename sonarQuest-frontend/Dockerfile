LABEL org.opencontainers.image.source=https://github.com/viadee/sonarQuest
FROM nginx:1.14.0-alpine
COPY ./dist/ /var/www/html/
COPY nginx/sonarquest.conf /etc/nginx/conf.d/default.conf
COPY nginx/nginx.conf /etc/nginx/nginx.conf

CMD ["nginx", "-g", "daemon off;"]
EXPOSE 80
