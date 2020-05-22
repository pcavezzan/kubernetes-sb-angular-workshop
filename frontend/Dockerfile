FROM nginxinc/nginx-unprivileged:1.20-alpine

USER root

RUN rm /usr/share/nginx/html/*

USER nginx

EXPOSE 8080

COPY --chown=nginx dist/frontend /usr/share/nginx/html
