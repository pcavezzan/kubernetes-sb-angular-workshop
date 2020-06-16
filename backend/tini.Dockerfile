####################################################################################################
# Following :
# - https://spring.io/guides/gs/spring-boot-docker/
# - https://blog.no42.org/code/docker-java-signals-pid1/
# - https://cloud.google.com/solutions/best-practices-for-building-containers?hl=fr
# - https://www.artindustrial-it.com/2017/09/20/10-best-practices-for-creating-good-docker-images/
####################################################################################################
FROM adoptopenjdk:11-jre-hotspot

# Add Tini
# Note: in an enterprise contexte, you would either:
#       - download tini, put it on your proxy storage server after having checked it signature then use download within
#         your secure internal storage server
#
#       - download directly from github but install GPG or use a parent image containing GPG and check signature
#
#       - check at least sha256 checksum
ARG TINI_VERSION=v0.19.0
RUN curl -Lo /usr/local/bin/tini https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini \
    && chmod +x /usr/local/bin/tini

RUN useradd -m -d /home/app app
WORKDIR /home/app

USER app

ENV JDK_JAVA_OPTIONS ""
EXPOSE 8080

COPY --chown=app:app target/backend.jar backend.jar
ENTRYPOINT [ "tini", "-e", "130", "-e", "143" , "--", "java", "-jar", "backend.jar" ]
