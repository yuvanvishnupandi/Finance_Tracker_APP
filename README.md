<div align="center">
  <img src="assets/Finvora_readme.png" alt="Finvora" width="470" style="border-radius:12px"/>
</div>

<br />

<div align="center">
<p>A production-grade, AI-powered personal finance tracker — with multi-LLM interactions, voice-activated logging, automated receipt scanning, and robust wealth management.</p>
<br />

<a href="LICENSE"><img alt="License" src="https://img.shields.io/badge/License-MIT-8B5CF6?style=flat-square&labelColor=1F2937" /></a>
 
<a href="https://github.com/yuvanvishnupandi/Finance_Tracker_APP/stargazers"><img alt="Stars" src="https://img.shields.io/github/stars/yuvanvishnupandi/Finance_Tracker_APP?style=flat-square&color=F59E0B&labelColor=1F2937&label=Stars" /></a>
 
<a href="https://github.com/yuvanvishnupandi/Finance_Tracker_APP/commits/main"><img alt="Last Commit" src="https://img.shields.io/github/last-commit/yuvanvishnupandi/Finance_Tracker_APP?style=flat-square&color=22C55E&labelColor=1F2937&label=Last%20Commit" /></a>

</div>

---

<div align="center">

<table>
  <tr>
    <td><img src="assets/img1.jpeg" width="400" style="border-radius:8px"/></td>
    <td><img src="assets/img2.jpeg" width="400" style="border-radius:8px"/></td>
  </tr>
  <tr>
    <td><img src="assets/img3.jpeg" width="400" style="border-radius:8px"/></td>
    <td><img src="assets/img4.jpeg" width="400" style="border-radius:8px"/></td>
  </tr>
  <tr>
    <td><img src="assets/img5.jpeg" width="400" style="border-radius:8px"/></td>
    <td><img src="assets/img6.jpeg" width="400" style="border-radius:8px"/></td>
  </tr>
  <tr>
    <td><img src="assets/img7.jpeg" width="400" style="border-radius:8px"/></td>
    <td><img src="assets/ai_assistant.jpeg" width="400" style="border-radius:8px"/></td>
  </tr>
</table>

</div>


---
## Key Functionalities
<div align="center">
<img src="assets/dashboard.png" alt="Finvora — overview" width="100%" />

</div>
<details>
<summary><b>See all features</b></summary>

<table>
<tr>
<td width="50%" valign="top">

#### 💰 Core financial tracking

- **Expense Tracking** — monitor total expenses, view spending trends, and receive automated insights.
- **Budget Management** — track overall budget utilization and monitor category-specific limits.
- **Savings Goals** — track progress bars and percentage gauges for specific financial targets.
- **Transaction History** — chronological logs of transactions with precise dates and times.
- **Data Export** — single-click exporting of financial data into PDF format.
- **Currency Converter** — instantly convert between different currencies.

</td>
<td width="50%" valign="top">

#### 🧠 Bleeding-edge AI features

- **Dedicated AI Assistant** — a conversational AI window that knows your exact balance, transactions, and goals.
- **Voice-Activated Chat** — inline voice prompting with infinite Google TTS chunking. Just talk to your AI advisor!
- **AI Receipt Scanner** — upload receipts to automatically extract names, amounts, and dates with zero manual entry.
- **Multi-LLM Engine** — resilient routing powered by Gemini, Mistral, and OpenAI APIs.
- **Predictive AI Alerts** — get real-time pop-ups when your spending habits approach danger zones.

</td>
</tr>
</table>

</details>

<br />

## Get started

```bash
git clone https://github.com/yuvanvishnupandi/Finance_Tracker_APP.git
cd Finance_Tracker_APP
```

Compile and run the Spring Boot backend server and the JavaFX frontend client. Detailed instructions are in the [Local Setup](#local-setup) below.


<br />

## Tech stack

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-FF0000?style=flat-square&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2-005C84?style=flat-square&logo=databricks&logoColor=white)
![Gemini](https://img.shields.io/badge/Gemini-8E75B2?style=flat-square&logo=googlegemini&logoColor=white)
![OpenAI](https://img.shields.io/badge/OpenAI-10A37F?style=flat-square&logo=openai&logoColor=white)
![Mistral](https://img.shields.io/badge/Mistral_AI-FA520F?style=flat-square&logo=mistralai&logoColor=white)

</div>

Frontend built on JavaFX + Maven. Backend on Spring Boot. Multi-LLM inference orchestrating Gemini Vision, OpenAI GPT, and Mistral. Data is stored locally in an embedded H2 database for blazing-fast access.

<br />

<h2 id="architecture">🏛️ Overall system architecture</h2>

The application follows a decoupled client-server architecture, allowing rapid local processing backed by cloud AI inference.

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

<br />

## Transaction processing workflow

The following sequence diagram illustrates how an AI receipt scan is processed into a transaction.

```mermaid
sequenceDiagram
actor User
participant Frontend
participant AI Engine
participant Backend
participant Database

User->>Frontend: Upload receipt image
Frontend->>AI Engine: Request AI analysis
AI Engine->>AI Engine: Extract Merchant Name
AI Engine->>AI Engine: Extract Total Amount
AI Engine->>AI Engine: Extract Date
AI Engine-->>Frontend: Return transaction JSON
Frontend->>Backend: Submit transaction details
Backend->>Database: Save new transaction
Database-->>Backend: Success
Backend-->>Frontend: Update Dashboard
Frontend-->>User: Display AI alert success pop-up
```

<br />


<h2 id="local-setup">🚀 Local setup</h2>

### Prerequisites

- JDK 17 or higher
- Maven 3.9+

### Clone repository

```bash
git clone https://github.com/yuvanvishnupandi/Finance_Tracker_APP.git
cd Finance_Tracker_APP
```

### Database
The H2 database is embedded and auto-generates on the first run. No external SQL server setup is required.

<details>
<summary><b>Backend setup</b></summary>

```bash
cd expense-tracker-springboot-server
mvn spring-boot:run
```

</details>

<details>
<summary><b>Frontend setup</b></summary>

```bash
cd expense-tracker-client
mvn compile javafx:run
```

</details>

<br />

<h2 id="environment-variables">Environment variables</h2>
<details>
<summary><b>Full reference</b></summary>

<br />

> AI API Keys are injected in `expense-tracker-client/src/main/java/org/example/services/AIEngine.java`.

| Variable | Description | Where |
|----------|-------------|-------|
| `server.port` | Backend API port (8080) | `backend/src/main/resources/application.properties` |
| `spring.datasource.url` | H2 Database connection string | `backend/src/main/resources/application.properties` |
| `GEMINI_KEY` | Google Gemini key for Vision Agent and reasoning | `AIEngine.java` |
| `MISTRAL_KEY` | Mistral AI key fallback | `AIEngine.java` |
| `OPENAI_KEY` | OpenAI API key powering text and fallback interactions | `AIEngine.java` |

</details>

<br />

## Data & storage

- **Database** — Embedded H2 (`expense-tracker-springboot-server/data/expense_tracker_db`)
- **Backend** — Spring Boot Server
- **AI service** — Native Java integration invoking REST endpoints to Google, OpenAI, and Mistral directly from the client.

<br />

## License

Finvora is [MIT licensed](LICENSE).
