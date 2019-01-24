FROM node:9.11.1 as build_container

COPY  . /usr/src/sonarQuest
WORKDIR /usr/src/sonarQuest
RUN chown node:node /usr/src/sonarQuest
USER node
RUN mkdir /home/node/.npm-global
ENV PATH=/home/node/.npm-global/bin:$PATH
ENV NPM_CONFIG_PREFIX=/home/node/.npm-global

RUN npm install -g @angular/cli
RUN npm install
RUN ng build




FROM nginx:1.14.0-alpine
COPY --from=build_container /usr/src/sonarQuest/dist/ /var/www/html/
COPY nginx/sonarquest.conf /etc/nginx/conf.d/default.conf
COPY nginx/nginx.conf /etc/nginx/nginx.conf

CMD ["nginx", "-g", "daemon off;"]
EXPOSE 80
