JAVASRC = src pom.xml
COMPOSE = DOCKER_BUILDKIT=1 COMPOSE_DOCKER_CLI_BUILD=1 docker-compose

.PHONY: default
default: build

.PHONY: build
build: target

.PHONY: docker
docker: target/zebra4j
	cp target/zebra4j docker/zebra4j
	$(COMPOSE) build zebracli

target: $(JAVASRC)
	./mvnw clean install

target/zebra4j: $(JAVASRC)
# Builds the image so that the user inside the container is the same as the calling user
# That way the container doesn't create files owned by root in the mounted volumes.
# Alternatively, both targets 'docker' and 'target/zebra4j' can be built completely in
# Docker as a multi-stage build - without running a container with volume. In that case,
# the Maven local repo will need to be mounted as cache -
# https://vsupalov.com/buildkit-cache-mount-dockerfile/. 
	UID=$(shell id -u) GID=$(shell id -g) $(COMPOSE) build native
	$(COMPOSE) run --rm native ./mvnw install -P native

