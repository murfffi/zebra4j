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
	UID=$(shell id -u) GID=$(shell id -g) $(COMPOSE) build native
	docker-compose run --rm native ./mvnw install -P native

