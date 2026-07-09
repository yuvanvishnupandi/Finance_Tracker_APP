# Deployment Staging & Production

Finvora is a powerful desktop client paired with a local backend server. This document dictates how to bundle the raw Java source code into distributable binaries for production end-users.

---

## 📦 Phase 1: Bundling the Spring Boot Backend

The backend utilizes an embedded H2 database, which means you do not need to configure an external database like PostgreSQL for deployment. The database `.db` file is dynamically generated in the `/data` folder on first execution.

To compile the backend into a standalone, production-ready Executable JAR:

```bash
cd expense-tracker-springboot-server

# Clean previous builds, package into a JAR, and skip tests for raw speed
mvn clean package -DskipTests
```

This generates `expense-tracker-springboot-server-0.0.1-SNAPSHOT.jar` in the `target/` directory. 

### Running in Production:
End users (or your startup script) can execute this JAR on any machine with Java installed:
```bash
java -jar target/expense-tracker-springboot-server-0.0.1-SNAPSHOT.jar
```

---

## 📦 Phase 2: Bundling the JavaFX Frontend

JavaFX applications require the JavaFX modules to be properly loaded onto the module path. Rather than forcing end-users to install Java and configure Maven, the optimal deployment strategy is to use **Jlink** or **JPackage**.

### Creating a Custom JRE with JLink

JLink parses the module dependencies and strips out the parts of the JDK that Finvora doesn't use, resulting in a highly compressed, custom Java Runtime Environment specifically for your app.

```bash
cd expense-tracker-client

# Generate the custom runtime image
mvn clean javafx:jlink
```

This produces a directory at `target/image/`.
This `image` directory contains the app, the required JavaFX DLLs, and the minimal Java runtime. You can zip this folder and send it to users. They simply double-click the `bin/expense-tracker-client.bat` file to run the app instantly!

---

## 🌐 Future: Cloud Web Deployment (SaaS Model)

If you plan to scale Finvora into a cloud-hosted web application:

1. **Migrate to PostgreSQL**: Swap the H2 dependencies in `pom.xml` for `postgresql`. Update `application.properties` with the cloud DB URI.
2. **Dockerize the Backend**: 
   ```dockerfile
   FROM eclipse-temurin:17-jre
   COPY target/expense-tracker-springboot-server-0.0.1-SNAPSHOT.jar app.jar
   ENTRYPOINT ["java", "-jar", "/app.jar"]
   ```
3. **Deploy to AWS/GCP**: Push the Docker image to Google Cloud Run or AWS Elastic Beanstalk.
4. **Update the Client**: The JavaFX client's API Base URL inside `SqlUtil.java` must be updated from `http://localhost:8080/api/v1` to your new production cloud URL (e.g., `https://api.finvora.com/v1`).
