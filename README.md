# residency-quarkus-secret-keys Project

The aim of this project is to showcase 
1. Using Quarkus Config API to read secret keys/values from Kubernetes `Secret` object (see docs [Kubernetes Config](https://quarkus.io/guides/kubernetes-config) and [Programmatically access the configuration](https://quarkus.io/guides/config#programmatically-access-the-configuration))
2. Utilize SmallRye `Secret Keys` capability to lock sensitive keys


## How it is implemented

* The `ConfigSourceInterceptor` class [DuplicateSecretKeysConfigSourceInterceptor.java](./src/main/java/org/acme/DuplicateSecretKeysConfigSourceInterceptor.java) is applied via [./src/main/resources/META-INF/services/io.smallrye.config.ConfigSourceInterceptor](./src/main/resources/META-INF/services/io.smallrye.config.ConfigSourceInterceptor) and implements the following for any key starting with **db.**

```shell script
public ConfigValue getValue(final ConfigSourceInterceptorContext context, final String name) {
```

* The [GreetingResource.java](./src/main/java/org/acme/GreetingResource.java) needs to unlock for usage the key `db.username` and `db.password` otherwise a `java.lang.SecurityException` will be thrown

```shell script
String dbUsernameUnlockedSecretValue = SecretKeys.doUnlocked(() -> {
   return (String) ConfigProvider.getConfig().getValue("db.username", String.class);
});
```        


## Create a db-credentials secret

```shell script
kubectl create secret generic db-credentials \
    --from-literal=db.username=admin \
    --from-literal=db.password=secret
```
## Testing

The application can be tested using:

```shell script
mvn clean compile test
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
## Deploying via quarkus-openshift plugin to openshift

 Having logged into an openshift cluster the application can be tested in openshift using:

```shell script
mvn clean package -Dquarkus.kubernetes.deploy=true
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-secret-example-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Access the RESTful API

```shell
http http://quarkus-secret-example-doh-dev.apps.sandbox.x8i5.p1.openshiftapps.com/hello/security
```

The output should look like this:

```shell
{db.password=secret, db.username=admin}
```

## Related Guides

- Kubernetes Config ([guide](https://quarkus.io/guides/kubernetes-config)): Read runtime configuration from Kubernetes ConfigMaps and Secrets
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
