FROM gcr.io/distroless/base-debian10

COPY docker/distroless/etc/passwd /etc/passwd
COPY docker/distroless/etc/group /etc/group

USER app:app

ENV GIN_MODE release
ENV STORE_DATA_PATH /app/data
ENV STORE_SERVER_ENV 9090

COPY --chown=app:app ./bin/store /app/store

CMD ["/app/store"]
