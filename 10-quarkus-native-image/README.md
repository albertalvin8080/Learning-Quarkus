# quarkus-native-image

## Creating a native executable

First, set the GRAALVM_HOME variable (without quotes, the parser doesn't like them)

```cmd
set GRAALVM_HOME=C:\Program Files\GraalVM\graalvm-jdk-22.0.2+9.1
```

There's a change you may need to manually prepare the environment for using msvc:

```cmd
path\to\vcvars64.cmd
```

Create the native image:

```cmd
mvnw.cmd package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```cmd
mvnw.cmd package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-native-image-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

