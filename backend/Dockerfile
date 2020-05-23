FROM adoptopenjdk:11-jre-hotspot

RUN useradd -m -d /home/app app

USER app

WORKDIR /home/app

EXPOSE 8080

COPY --chown=app target/backend.jar backend.jar

ENTRYPOINT [ "java", "-jar", "backend.jar" ]
