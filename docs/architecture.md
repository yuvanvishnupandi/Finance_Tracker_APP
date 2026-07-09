# System Architecture & Diagrams

This document details the system design, communication protocols, request lifecycles, and security boundaries of the Finvora Finance Tracker environment.

---

## 🏗️ High-Level System Architecture

Finvora uses a highly decoupled Client-Server architecture to facilitate rapid local operations backed by powerful cloud AI processing.

```mermaid
graph TD
    subgraph Client Layer
        A[JavaFX Desktop App]
    end

    subgraph Internal Network Interfaces
        B[Local HTTP API Gateway]
        C[File System (PDF / CSV)]
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

---

## 🚦 Request & Data Lifecycles

### 1. HTTP REST Authentication & Data Lifecycle

Below is the sequence of auth verification and header mapping when accessing the local Spring Boot backend:

```mermaid
sequenceDiagram
    autonumber
    actor Client as JavaFX Client
    participant Controller as Spring Boot REST Controller
    participant Service as Business Logic Service
    participant Repo as Spring Data JPA Repo
    participant DB as H2 Database

    Client->>Controller: GET /api/v1/transaction/user/{userId}
    Note over Controller: Validates User ID format
    alt Invalid Input
        Controller-->>Client: 400 Bad Request
    else Valid Input
        Controller->>Service: fetchTransactions(userId)
        Service->>Repo: findByUserIdOrderByTransactionDateDesc()
        Repo->>DB: SELECT * FROM transaction WHERE user_id = ?
        DB-->>Repo: Return ResultSet
        Repo-->>Service: Return Entity List
        Service->>Service: Map Entities to DTOs
        Service-->>Controller: Return DTO List
        Controller-->>Client: 200 OK (JSON Data)
    end
```

### 2. Multi-LLM Resilient AI Routing Flow

The AI Intent Router uses a cascading fallback mechanism to ensure 100% uptime when making API queries.

```mermaid
sequenceDiagram
    autonumber
    actor User as Finvora User
    participant Router as AIEngine Router
    participant Gemini as Gemini API
    participant Mistral as Mistral API
    participant OpenAI as OpenAI API

    User->>Router: "I spent $50 on food just now"
    Note over Router: Injects Current Local Time Context
    Router->>Gemini: POST generateContent()
    
    alt Gemini Success
        Gemini-->>Router: 200 OK (JSON Intent)
    else Gemini Rate Limit / Outage (429/500)
        Router-->>Router: Catch Exception & Fallback
        Router->>Mistral: POST /v1/chat/completions
        
        alt Mistral Success
            Mistral-->>Router: 200 OK (JSON Intent)
        else Mistral Timeout
            Router-->>Router: Catch Exception & Fallback
            Router->>OpenAI: POST /v1/chat/completions
            OpenAI-->>Router: 200 OK (JSON Intent)
        end
    end
    
    Router->>User: Resolves Intent & Inserts Transaction
```

---

## 🛡️ Security Boundaries

We isolate operational layers to block arbitrary access to user financial data:

```mermaid
graph LR
    subgraph Desktop OS Environment
        A[JavaFX App Process]
    end

    subgraph Secure Local Perimeter (127.0.0.1)
        B[Spring Boot App Router]
    end

    subgraph Internal File System (No External Access)
        D[(H2 Database file .db)]
    end

    subgraph Public Internet
        E[External AI Providers]
    end

    A -->|REST API over localhost| B
    B -->|JPA JDBC Connection| D
    A -->|HTTPS / TLS 1.3| E
```

---

## 💻 Developer Workflow

The lifecycle of developer updates from local editor to final application compilation:

```mermaid
graph TD
    A[Write Code Locally] --> B[Run Maven Compile]
    B -->|Check Dependencies| C{Valid POMs?}
    C -->|Invalid| D[Maven Build Failure]
    C -->|Valid| E[Compile Java Sources to .class]
    E --> F[Run JUnit Tests]
    F -->|Tests Fail| G[Reject Build]
    F -->|Passes| H[Generate Executable JAR & JLink Runtime]
    H --> I[End User Delivery]
```
