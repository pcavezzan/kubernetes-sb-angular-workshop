# Example of custom Java runtime using jlink in a multi-stage container build
FROM eclipse-temurin:17-jdk as jre-build
# Create a custom Java runtime
RUN $JAVA_HOME/bin/jlink \
         --add-modules java.se,jdk.management \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /javaruntime

# Define your base image
FROM debian:buster-slim
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-build /javaruntime $JAVA_HOME

# Continue with your application deployment
RUN useradd -m -d /home/app app
USER app
WORKDIR /home/app
ENV JDK_JAVA_OPTIONS ""
EXPOSE 8080
COPY --chown=app:app target/backend.jar backend.jar
ENTRYPOINT [ "java", "-jar", "backend.jar" ]
