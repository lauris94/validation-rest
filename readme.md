The application is made up using three tier architecture, which consists of presentation, business logic and data access
layers.

Using lombok to reduce boilerplate code (getters, setters, constructors, equals and hashcode)

Using jacoco maven plugin to see test coverage

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method
in the `com.laurynas.workorder.validationrest.ValidationRestApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## UI

validating work order in plain json format:
[http://localhost:8080/api/validationForm](http://localhost:8080/api/validationForm)

work order validation history:
[http://localhost:8080/api/viewOrders](http://localhost:8080/api/viewOrders)
