FROM ghcr.io/graalvm/graalvm-ce:java11-21.2
RUN gu install native-image
# Ensure that container runs as a non-root user with a provided uid:gid
# See Makefile and this document for reasoning:
# https://jtreminio.com/blog/running-docker-containers-as-current-host-user/#ok-so-what-actually-works
ARG GID=1000
ARG UID=1000
RUN groupadd -g ${GID} mvnuser &&\
    useradd -l -u ${UID} -g mvnuser -d /home/mvnuser mvnuser &&\
    install -d -m 0755 -o mvnuser -g mvnuser /home/mvnuser
USER mvnuser:mvnuser

