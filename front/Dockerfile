# Front-end compile and build angular codebase
FROM node:latest as build-front
WORKDIR /usr/local/app
COPY ./ /usr/local/app/
RUN npm install
ARG env=prod
RUN npm run build

# Front-end with Nginx server 
FROM nginx:latest
COPY --from=build-front /usr/local/app/dist/front /usr/share/nginx/html
COPY ./nginx-server.conf /etc/nginx/conf.d/default.conf