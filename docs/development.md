# Developer Onboarding & Style Guidelines

This document describes how to set up, run, format, and contribute to the Finvora codebase.

---

## 🛠️ Prerequisites

Before you start, make sure you have installed:

- **JDK**: Version `17` or `21` (LTS releases are strongly recommended).
- **Maven**: Apache Maven `3.9.x` or higher for building the monolithic repos.
- **Git**: For version control.

---

## 🚀 Quick Local Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yuvanvishnupandi/Finance_Tracker_APP.git
   cd Finance_Tracker_APP
   ```

2. **Clean & Compile**:
   The project is split into two independent Maven projects. You must compile both.
   ```bash
   # Backend
   cd expense-tracker-springboot-server
   mvn clean compile
   
   # Frontend
   cd ../expense-tracker-client
   mvn clean compile
   ```

3. **Configure API Keys**:
   To test the AI components locally, insert your development API keys directly into `AIEngine.java` and `AIVoiceService.java`. *Warning: Never commit these keys to version control.*

---

## 🖌️ Code Style Guidelines

Finvora enforces strict coding styles to ensure a clean, maintainable architecture.

### JavaFX UI Components
- **Zero FXML Policy**: All user interface components *must* be constructed programmatically in pure Java. `FXMLLoader` and `.fxml` files are explicitly forbidden to ensure blazing-fast rendering and strict compile-time type safety.
- **Component Isolation**: Break complex views into small, reusable components inside `org.example.components`. Example: `TransactionComponent.java`.
- **CSS Styling**: Do not use inline styles (`node.setStyle()`). Always use external CSS classes defined in `style.css`.
  ```java
  // ❌ Bad
  label.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
  
  // ✅ Good
  label.getStyleClass().addAll("text-light-gray", "text-size-md");
  ```

### Spring Boot Backend
- **Fat Controllers are Banned**: REST Controllers (`@RestController`) should only handle HTTP validation and routing. All business logic *must* live inside `@Service` classes.
- **DTO Mappings**: Entities must not be sent directly over the wire if they expose sensitive fields. 

---

## 🔍 Pull Request (PR) Process

1. Create a feature branch: `git checkout -b feature/cool-new-idea`.
2. Write clean, documented code adhering to the style guidelines.
3. Write standard JUnit test cases for any backend business logic modifications.
4. Run `mvn clean test` across the workspace to ensure you haven't broken the build.
5. Submit a PR targeting the `main` branch with a highly descriptive summary of your changes.
