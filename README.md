# Simple Library System

A small RESTful library management service built with Spring Boot and Maven.

## Requirements

- Java 17+
- Maven
- Docker (for integration tests and dockerized run)

## Building and Testing

Build package and run unit tests:

```bash
mvn clean package
```

Note: Docker should be installed and running before running integration tests.

## Running locally

Run the application:

```bash
mvn spring-boot:run
```

Swagger UI: http://localhost:8082/swagger-ui/index.html

## Release Docker Image

```bash
mvn jib:build -Dimage=simple-library-system:latest
```

## Running in Docker

Start services with Docker Compose:

```bash
docker compose up -d
```

Swagger UI (docker): http://localhost:8081/swagger-ui/index.html

## Running in Kubernetes

Apply the Kubernetes manifests:

```bash
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secrets.yaml
kubectl apply -f k8s/deployment.yaml
```

## API Overview

Base path: `/`

All endpoints return JSON. Standard HTTP status codes are used (200, 400).

### Books

- `GET /books`  
  List books.  
  Example:
  ```bash
  curl -s "http://localhost:8082/books"
  ```

- `POST /api/books`  
  Create a book. Body:
  ```json
  {
    "name": "Clean Code",
    "author": "DR",
    "isbn": "9780132350884"
  }
  ```


- `PATCH /books/{id}/borrowers/{id}`  
  Borrow a book.

- `PATCH /books/{id}/borrowers/{id}/return`  
  Return a book.


## API docs

OpenAPI/Swagger UI is available at the URLs in the Running sections.
Swagger UI: http://localhost:8082/swagger-ui/index.html


