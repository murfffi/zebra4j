FROM ghcr.io/graalvm/graalvm-ce:java11-21.0.0.2
RUN gu install native-image
ARG GID
ARG UID
RUN groupadd -g ${GID} mvnuser &&\
    useradd -l -u ${UID} -g mvnuser -d /home/mvnuser mvnuser &&\
    install -d -m 0755 -o mvnuser -g mvnuser /home/mvnuser
USER mvnuser:mvnuser

