# quarkus-dockerizing

## Using Dockerfile

```shell
mvnw.cmd package
```

```shell
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus-dockerizing-jvm .
```

```shell
docker run -i --rm -p 8080:8080 quarkus/quarkus-dockerizing-jvm
```

### Using distroless (Dockerfile.native-micro) native-image instead of a JAR

Set the GRAALVM_HOME variable:

```shell
set GRAALVM_HOME=C:\Program Files\GraalVM\graalvm-jdk-22.0.2+9.1
```

Package the app using native profile:

> NOTE: You must use `-Dquarkus.native.container-build=true` (this command runs a container which contains a GraalVM) because if you're using toolchains from macOS or Windows, the native-image won't be compiled for the target docker image (which is linux).

```shell
mvnw.cmd package -Pnative -Dquarkus.native.container-build=true
```

```shell
docker build -f src/main/docker/Dockerfile.native-micro -t quarkus/quarkus-dockerizing .
```

```shell
docker run -i --rm -p 8080:8080 quarkus/quarkus-dockerizing
```

## Using `quarkus-container-image-jib` or `quarkus-container-image-docker`

You simply need to add one of them to your POM.xml and then run:

> NOTE: Configuration property 'quarkus.package.type' has been deprecated and replaced by: [quarkus.package.jar.enabled, quarkus.package.jar.type, quarkus.native.enabled, quarkus.native.sources-only]
```shell
mvnw.cmd package -Dquarkus.container-image.build=true -Dquarkus.package.jar.type=uber-jar
```

```shell
docker run -i --rm -p 8080:8080 albert/quarkus-dockerizing:1.0-SNAPSHOT
```

There are also some properties which you can use to change the container image.