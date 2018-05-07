FROM node:9.11.1
COPY  . /usr/src/sonarQuest
WORKDIR /usr/src/sonarQuest
RUN chown node:node /usr/src/sonarQuest
USER node
RUN mkdir /home/node/.npm-global
ENV PATH=/home/node/.npm-global/bin:$PATH
ENV NPM_CONFIG_PREFIX=/home/node/.npm-global
RUN npm install -g @angular/cli
RUN npm install
EXPOSE 4200
ENTRYPOINT ["npm", "start"]
