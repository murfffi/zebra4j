# syntax=docker/dockerfile:1.2.1

FROM gcr.io/distroless/base-debian10:latest@sha256:6ec6da1888b18dd971802c2a58a76a7702902b4c9c1be28f38e75e871cedc2df
# https://github.com/opencontainers/image-spec/blob/master/annotations.md
LABEL org.opencontainers.image.authors="murfffi@gmail.com"
LABEL org.opencontainers.image.source="https://github.com/murfffi/zebra4j.git"
LABEL org.opencontainers.image.url="https://github.com/murfffi/zebra4j"
LABEL org.opencontainers.image.licenses="LGPL-3.0"

WORKDIR /root
# https://github.com/GoogleContainerTools/distroless/issues/217
COPY --from=debian:stable-slim /lib/x86_64-linux-gnu/libz.so.1 /lib/x86_64-linux-gnu/libz.so.1

COPY --chmod=700 zebra4j zebra4j
ENTRYPOINT [ "/root/zebra4j" ]
