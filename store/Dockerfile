FROM ubuntu:18.04

RUN useradd -m -d /app app

USER app

WORKDIR /app

ENV GIN_MODE release
ENV STORE_DATA_PATH /app/data
ENV STORE_SERVER_ENV 9090

COPY --chown=app:app ./bin/store .

ENTRYPOINT ["./store"]
