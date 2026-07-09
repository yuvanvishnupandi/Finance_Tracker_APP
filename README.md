# Finvora - Finance Tracker

Finvora is a production-grade, AI-powered personal finance tracker and wealth management workspace. It features dynamic budgeting, savings goal tracking, and interactive AI financial assistance.

## Features

- Multi-LLM AI Fallback Engine: Chat seamlessly with an AI advisor powered by a resilient engine routing between various AI APIs to prevent downtime.
- Voice-Activated AI Logging: Inline voice prompting parses spoken commands, grabs the exact time, and logs the transaction.
- AI Receipt Scanner: Upload JPEG or PDF receipts and watch the AI extract transaction names, amounts, and dates with zero manual entry.
- Dynamic Budgeting & Savings Goals: Set up custom savings goals and monitor monthly budgets with real-time visual progress bars.
- Precision Time & Date Tracking: Advanced TimePicker UI allows logging exactly when transactions happen.
- PDF Report Generation: Export structured PDF financial reports directly from the dashboard.

## System Architecture

The application follows a decoupled client-server architecture. 

```mermaid
graph TD
    subgraph Client Layer
        A[JavaFX Desktop App]
    end

    subgraph Internal Network Interfaces
        B[Local HTTP API Gateway]
        C[File System PDF / CSV]
    end

    subgraph Server Services
        D[Spring Boot REST Controller]
        E[AI Routing Engine]
        F[AIVision & AIVoice Services]
    end

    subgraph External AI APIs
        Gemini[Google Gemini API]
        Mistral[Mistral API]
        OpenAI[OpenAI API]
    end

    subgraph Storage Layer
        G[(H2 Local Database)]
    end

    A -->|HTTP Requests| B
    A -->|File I/O| C
    B --> D
    A --> E
    A --> F
    D --> G
    
    E -->|Primary LLM| Gemini
    E -.->|Fallback 1| Mistral
    E -.->|Fallback 2| OpenAI
```

## Tech Stack

### Frontend (Client)
- JavaFX
- Maven
- Gson
- Apache PDFBox
- JavaFX MediaPlayer

### Backend (Server)
- Spring Boot (Java 17)
- Spring Data JPA & Hibernate
- H2 Database

## Prerequisites

- JDK 17 or higher
- Maven 3.9+

## Environment Setup

**Backend (expense-tracker-springboot-server/src/main/resources/application.properties)**
```properties
server.port=8080
spring.datasource.url=jdbc:h2:file:./data/expense_tracker_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

**Frontend AI APIs (expense-tracker-client/src/main/java/org/example/services/AIEngine.java)**
Ensure you inject your API keys here before compiling:
```java
private static final String GEMINI_KEY = "YOUR_GEMINI_API_KEY";
private static final String MISTRAL_KEY = "YOUR_MISTRAL_API_KEY";
private static final String OPENAI_KEY = "YOUR_OPENAI_API_KEY";
```

## Quick Run Instructions

Start the backend API server:
```bash
cd expense-tracker-springboot-server
mvn spring-boot:run
```

Start the frontend JavaFX application (in a new terminal):
```bash
cd expense-tracker-client
mvn compile javafx:run
```

## Testing Commands

Run the Maven test suites:

```bash
# Run backend tests
cd expense-tracker-springboot-server
mvn test

# Run frontend tests
cd expense-tracker-client
mvn test
```

## Deployment

Deploy the backend as a standard Spring Boot executable JAR and the frontend as a bundled JavaFX executable. 

```bash
# Build the backend JAR
cd expense-tracker-springboot-server
mvn clean package
java -jar target/expense-tracker-springboot-server-0.0.1-SNAPSHOT.jar
```

## License
This project is licensed under the MIT License.
