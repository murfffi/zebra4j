# syntax=docker/dockerfile:1.2.1

# using nonroot because of
# https://cloud.google.com/architecture/best-practices-for-operating-containers#avoid_running_as_root

# nonroot is currently documented at 
# https://github.com/GoogleContainerTools/distroless/issues/235#issuecomment-503733486
FROM gcr.io/distroless/base-debian10:nonroot
# base-debian10 + an additional .so file are required for graalvm-produced binaries. See below.

# https://github.com/opencontainers/image-spec/blob/master/annotations.md
LABEL org.opencontainers.image.authors="murfffi@gmail.com"
LABEL org.opencontainers.image.source="https://github.com/murfffi/zebra4j.git"
LABEL org.opencontainers.image.url="https://github.com/murfffi/zebra4j"
LABEL org.opencontainers.image.licenses="LGPL-3.0"

# Required for graalvm binaries - https://github.com/GoogleContainerTools/distroless/issues/217
COPY --from=debian:stable-slim /lib/x86_64-linux-gnu/libz.so.1 /lib/x86_64-linux-gnu/libz.so.1

WORKDIR /home/nonroot
COPY --chmod=700 --chown=nonroot zebra4j zebra4j
ENTRYPOINT [ "./zebra4j" ]
