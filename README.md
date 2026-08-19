# Spring Boot REST API Docker Practical

This repository contains a simple Spring Boot REST API packaged into an executable JAR file and deployed inside a Docker container, tested via Postman.

---

## 1. Application Architecture

```
+-------------------+      HTTP GET / POST Request      +-------------------------+
|   User / Postman  |  ==============================>  |  Spring Boot Application|
|                   |                                   |    (Running in Docker)  |
+-------------------+                                   +------------+------------+
          ^                                                          |
          |               JSON HTTP Response                         | Dispatch to Controller
          |  <====================================================== v
+---------+---------+                                   +-------------------------+
| HTTP Response /   |                                   |     RestController      |
| Status Code (200) |                                   |   (ApiController.java)  |
+-------------------+                                   +-------------------------+
```

### Architecture Flow:
1. **User / Postman**: Sends an HTTP Request (e.g. `GET http://localhost:8080/api/hello` or `POST http://localhost:8080/api/echo`).
2. **Spring Boot Application (Docker Container)**: Receives the network request routed from Docker host port `8080` to container port `8080` via embedded Tomcat.
3. **Controller (`ApiController.java`)**: The `@RestController` processes the incoming request path and request parameters/body.
4. **HTTP Response**: The controller returns a structured JSON payload wrapped in `ResponseEntity<ApiResponse>` with HTTP Status code `200 OK`.

---

## 2. Project Structure

```
springboot-docker-restapi/
├── Dockerfile                  # Multi-stage Docker build configuration
├── docker-compose.yml          # Docker compose file for quick deployment
├── pom.xml                     # Maven build and dependencies configuration
├── Postman_Collection.json     # Ready-to-import Postman test collection
├── README.md                   # Detailed practical manual
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── demo/
        │               ├── DemoApplication.java    # Spring Boot Main App
        │               ├── controller/
        │               │   └── ApiController.java  # REST Endpoints
        │               └── model/
        │                   ├── ApiResponse.java    # Standard response DTO
        │                   └── EchoRequest.java    # Request Body DTO
        └── resources/
            └── application.properties              # Server port 8080
```

---

## 3. Step-by-Step Practical Execution Guide

### Step 1: Package as JAR & Build Docker Image
Run the following command in the project root:
```bash
docker build -t springboot-docker-restapi:1.0.0 .
```

### Step 2: Run the Docker Container
Run the container in detached mode and map port `8080`:
```bash
docker run -d -p 8080:8080 --name springboot-rest-app springboot-docker-restapi:1.0.0
```

To view logs from the running container:
```bash
docker logs -f springboot-rest-app
```

### Step 3: Test Endpoints using Postman

#### Method A: Import Postman Collection
1. Open **Postman**.
2. Click **Import** (top left).
3. Select the file `Postman_Collection.json` from this project directory.
4. Run any of the pre-configured requests.

#### Method B: Manual Testing in Postman

| Method | URL | Headers | Body (Raw JSON) | Expected Response |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `http://localhost:8080/api/hello` | None | None | Greeting message with status `SUCCESS` |
| **GET** | `http://localhost:8080/api/greet?name=YourName` | None | None | Personalized greeting |
| **GET** | `http://localhost:8080/api/info` | None | None | Architecture info & metadata |
| **POST** | `http://localhost:8080/api/echo` | `Content-Type: application/json` | `{"name": "Alice", "message": "Docker Practical"}` | Echoed payload with timestamp |

---

## 4. Useful Docker Commands

- **Check Running Container**: `docker ps`
- **Stop Container**: `docker stop springboot-rest-app`
- **Start Container**: `docker start springboot-rest-app`
- **Remove Container**: `docker rm -f springboot-rest-app`
- **Inspect Images**: `docker images`
