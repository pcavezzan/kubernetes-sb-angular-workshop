FROM gcr.io/distroless/java:11

USER nonroot:nonroot

COPY --chown=nonroot target/backend.jar /app/backend.jar

WORKDIR /app

EXPOSE 8080

CMD [ "backend.jar" ]
