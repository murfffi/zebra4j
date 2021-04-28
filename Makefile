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
	docker-compose run --rm native

