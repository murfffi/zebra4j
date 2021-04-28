# syntax=docker/dockerfile:1.2.1

FROM gcr.io/distroless/base-debian10
WORKDIR /root
COPY --chmod=700 zebra4j zebra4j
# https://github.com/GoogleContainerTools/distroless/issues/217
COPY --from=debian:stable-slim /lib/x86_64-linux-gnu/libz.so.1 /lib/x86_64-linux-gnu/libz.so.1
ENTRYPOINT [ "/root/zebra4j" ]
