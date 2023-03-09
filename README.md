# PDV MS-User-Registry

Spring Application implementing the **User-Registry** microservice for the **Personal Data Vault** Project.

---

## Dependencies

Upstream:
- PagoPA products

Downstream:
- [pdv-ms-person](https://github.com/pagopa/pdv-ms-person)
- [pdv-ms-tokenizer](https://github.com/pagopa/pdv-ms-tokenizer)

---

## Api Documentation 📖

See the [UAT here.](https://api.uat.pdv.pagopa.it/docs/pdvuapis/openapi.json)

See the [PROD here.](https://api.pdv.pagopa.it/docs/pdvpapis/openapi.json)


---

## Technology Stack 📚

- SDK: Java 11
- Framework: Spring Boot
- Cloud: AWS

---

## Develop Locally 💻

### Prerequisites

- git
- maven
- jdk-11

### How to run unit-tests

using `junit`

```
./mvnw clean test
```

### How to run locally 🚀

This microservice needs the two down-stream dependencies (*pdv-ms-person* and *pdv-ms-tokenizer*) to be up and running
to perform actions.

So, first step if you want to test it locally, run these two microservices following instructions on these links:

- [pdv-ms-person](https://github.com/pagopa/pdv-ms-person)
- [pdv-ms-tokenizer](https://github.com/pagopa/pdv-ms-tokenizer)

Then, set the following Environment Variables:

| **Key**         | **Value**                |
|-----------------|--------------------------|
| APP_SERVER_PORT | default: 8080[^app_port] |
| APP_LOG_LEVEL   | default: DEBUG           |

[^app_port]: When running multiple microservices simultaneously, a different port must be chosen for each one.

From terminal, inside the **app** package:

```
../mvnw spring-boot:run
```

---

## Mainteiners 👷🏼

See `CODEOWNERS` file
