# Spring Rx2 JDBC

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](/LICENSE)

Spring Boot 2 WebFlux and Reactive RDBMS Connection using Rx2Java JDBC, with CRUD as usecase.

### Stacks

- [Kotlin 1.3.50](https://blog.jetbrains.com/kotlin/2019/08/kotlin-1-3-50-released/)
- [Spring Boot 2.2.x](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.2-Release-Notes)
- [Rx2Java JDBC v0.2.5](https://github.com/davidmoten/rxjava2-jdbc), RxJava2 integration with JDBC including Non-blocking Connection Pools.
- Postgresql 10
- Gradle 5.6.2 + Kotlin DSL
- Vagrant + Ansible, for bootstrap & provisioning the development environment.

### Running application

- __Provision dev env with Vagrant__

  ```bash
  # Starting vagrant vm and provision for first time
  vagrant up

  # Re-provisioning vagrant
  vagrant provision
  ```

- __Running application__

  ```bash
  ./gradlew bootRun
  ```

  Url is: `http://localhost:8084/`

### REST API Endpoints

| HTTP Method | Path          | Description                        |
| ----------- | --------------| -----------------------------------|
| GET         | /persons      | Get all existing person data.      |
| GET         | /persons/{id} | Get existing person data by Id.    |
| POST        | /persons      | Insert new person data.            |
| PUT         | /persons/{id} | Update existing person data by Id. |
| DELETE      | /persons/{id} | Delete existing person data by Id. |
| GET         | /ping         | Testing endpoing.                  |

## License

License under the MIT license. See [LICENSE](/LICENSE) file.<Paste>

