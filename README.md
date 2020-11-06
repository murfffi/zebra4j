# zebra4j - Zebra puzzle generator and solver

[![Build Status](https://travis-ci.org/murfffi/zebra4j.svg?branch=main)](https://travis-ci.org/murfffi/zebra4j)

## Overview

zebra4j is a pure Java generator and solver for [Zebra puzzles](https://en.wikipedia.org/wiki/Zebra_Puzzle),
also knows as "logic grid puzzles".

The library requires Java 8+. `/zebra4j/src/main/java/zebra4j/App.java`
and unit tests demonstrate how to use it.

## Download a release

The library is available in the Maven repository <https://dl.bintray.com/marin-nozhchev/marin-nozhchev> .

The artifact details are:

- groupId: zebra4j
- artifactId: zebra4j
- version: 0.1

With Maven, you can add it as a dependency like this:

```xml
<project>
  ...
  <repositories>
    ...
    <repository>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
      <id>bintray-marin-nozhchev</id>
      <url>https://dl.bintray.com/marin-nozhchev/marin-nozhchev</url>
    </repository>
    ...
  </repositories>
  ...
  <dependencies>
   ...
    <dependency>
      <groupId>zebra4j</groupId>
      <artifactId>zebra4j</artifactId>
      <version>0.1</version>
    </dependency>
  ...
```

## Install library from source

Requirements:
- Java 8 JDK with its java executable on the PATH

To install, check out the code and then run:

```bash
./mvnw install
```

## Submit bugs and feature requests

Please use [Github issues](https://github.com/murfffi/zebra4j/issues).

## Logging

The library uses slf4j-api as logging API and does not include or enforce any particular backend.

## Contributing

See CONTRIBUTING.md .
