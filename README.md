# Hexagonal Template

Can be used as a starting point for ports and adapters or hexaginal architecture based applications.

It is free to use.

## Build
run `gradle build`

or use gradle wrapper `./gradlew build` (or use the gradlew.bat file for Windows OS)

## Run locally
Run TemplateApplication

## Test locally
Swagger UI available at :
http://localhost:8080/q/swagger-ui/

## Links
If you want to generate a template from scratch, you can visit [Quarkus Setup Wizard](https://code.quarkus.io/) to generate a basic setup.
You can introduce modules cfr this template on the go as you need. 

## Remarks
### Get Started
If you want to start a new Quarkus project I suggest using the Quarkus Setup Wizard (see links). This template is simply for demo purposes or
you want to see Quarkus combined in a hexagon setup.

### Mac / MX - aarch64 architecture
For Mac users having aarch64 based architecture, check [installation info](https://dev.to/maksimrv/install-graalvm-on-macos-m1-1p8n)

### Java 17 GraalVM Updater - GraalVM directory is invalid 
For Java 17 graalvm installations; the default recommended way of settings GRAALVM_HOME etc. didn't work for me. 
For some reason I had to use the gu binary located in ../Contents/Home/lib/installer/bin/gu.

Using the one under ../Contents/Home/bin/gu or using path did **not** work for me. When trying to do so I received following 
error:

> Error: The GraalVM directory /Library/Java/JavaVirtualMachines/graalvm-ce-java17-22.3.1/Contents is invalid.

Setting GRAALVM_HOME and GRAAL_HOME, but it did not fix the issue. It seems others suffered this issue as well : [GitHub Issue Link](https://github.com/oracle/graal/issues/2231).

You can install the native-image component using the one available under ../Contents/Home/lib/installer/bin/gu. After installing
the native-image component, you should have no issue building the native image.

Note : For newer GraalVM installations (tested with GraalVM v21.x.x) the above steps are not necessary anymore.

## Generated Quarkus Documentation :

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

### Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

### Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable
with: `./build/be.yonicon.template-java17-quarkus-gradle-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
### Related Guides

- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time
  processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions
  that depend on it.
- Agroal - Database connection pool ([guide](https://quarkus.io/guides/datasource)): Pool JDBC database connections (
  included in Hibernate ORM)

### Provided Code

#### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
