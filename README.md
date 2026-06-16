<div align="center">
  <img src="./dashboard.jpg" alt="Finance Tracker Dashboard" width="100%" />

  <h1>Finance Tracker App</h1>
  <p>A comprehensive, full-stack personal finance management solution designed to track, analyze, and grow your wealth.</p>
</div>

## 📖 Overview

**Finance Tracker** is a robust web application built with a modern tech stack to provide users with an intuitive, seamless experience in managing their personal finances. From tracking daily expenses to long-term savings goals, the application centralizes financial data into a dynamic, user-friendly interface.

## ✨ Features

- **Expense Tracking:** Effortlessly add, categorize, and monitor your daily spending.
- **Analytics Dashboard:** Visualize your financial health with smart charts, income vs. expense comparisons, and spending trends.
- **Budget Management:** Set customizable budget limits, receive spending alerts, and track category-specific goals.
- **Transaction History:** Search and filter your transaction history with a detailed timeline view.
- **Financial Reports:** Generate and export monthly financial reports (PDF, CSV, Excel formats).
- **Savings Goals:** Create custom savings goals and track milestone achievements over time.

## 🏗️ System Architecture

The project follows a standard decoupled Client-Server architecture to ensure high scalability and separation of concerns.

- **Frontend (Client):** Handles user interactions, data visualization, and routing. Communicates with the backend via RESTful APIs.
- **Backend (Server):** Built with Spring Boot. Manages business logic, authentication, request validation, and database transactions.
- **Database Layer:** A relational database (MySQL/PostgreSQL) used to persist users, transactions, budgets, and savings goals securely.

## 🛠️ Tech Stack

**Client-Side:**
- Modern UI components and responsive design
- Advanced Data Visualization Libraries (Charts & Analytics)

**Server-Side:**
- Java 17+
- Spring Boot (Spring Web, Spring Data JPA, Spring Security)
- Maven Build Tool

**Database & Infrastructure:**
- MySQL / PostgreSQL
- RESTful API Architecture

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 17+
- Node.js & npm (if using a web-based client)
- MySQL / PostgreSQL Server

### 1. Database Configuration
Update your database credentials in the backend application properties:
```properties
# expense-tracker-springboot-server/src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_tracker
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

### 2. Running the Backend (Spring Boot)
Open your terminal and navigate to the backend directory:
```bash
cd expense-tracker-springboot-server
./mvnw clean install
./mvnw spring-boot:run
```
The API will be available at `http://localhost:8080`.

### 3. Running the Frontend (Client)
In a new terminal window, navigate to the frontend directory:
```bash
cd expense-tracker-client
npm install
npm start
```

## 🔒 Security & Privacy
This repository utilizes a comprehensive `.gitignore` to ensure that no system-specific configurations, IDE files, environment variables, or sensitive data are inadvertently committed to version control.

## 🤝 Contributing
Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://github.com/yuvanvishnupandi/Finance_Tracker_APP/issues).
