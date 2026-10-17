# 🧠 Online Quiz & Assessment System

A production-grade, modular backend application built with **Java 17, Spring Boot, Spring Security, and MySQL**. It features category-wise quizzes, automated scoring, user attempt tracking, global leaderboards, role-based authorization, and automated database migrations using Flyway.

---

### 🌟 Key Highlights & Architecture

- **Role-Based Access & Security:** Custom `RoleAuthFilter` and `SecurityConfig` to protect administrative endpoints versus student attempt routes.
- **Quiz Management & Categorization:** Supports organizing quizzes by category with dynamic question selection and options mapping.
- **Submission & Attempt Tracking:** Real-time answer evaluation via `QuizAttemptService` and dynamic score calculation.
- **Live Leaderboard:** Tracks and ranks user performances across quizzes (`LeaderboardController` & `LeaderboardService`).
- **Database Versioning:** Managed schema lifecycle with Flyway migrations (`V1__init_schema.sql`, `V2__create_question_and_option_tables.sql`)[cite: 5].
- **Clean DTO Pattern & Error Handling:** Strictly decoupled request/response payloads with a centralized `GlobalExceptionHandler`[cite: 5].
- **Containerization Ready:** Includes `Dockerfile` for seamless deployment to Docker and cloud hosting platforms like Render[cite: 4, 5].

---

### 🛠️ Tech Stack

- **Backend:** Java, Spring Boot, Spring Data JPA, Spring Security[cite: 5]
- **Database:** MySQL, Flyway (DB Migration)[cite: 5]
- **Frontend / Client UI:** Static web client (`index.html`)[cite: 5]
- **DevOps & Tools:** Docker, Maven (`mvnw`), Git[cite: 4, 5]

---

### 📂 Project Structure Overview

```text
src/main/java/quiz/
├── config/          # RoleAuthFilter, SecurityConfig[cite: 5]
├── controller/      # Category, Question, Quiz, QuizAttempt, Leaderboard, User[cite: 5]
├── dto/             # Request & Response Data Transfer Objects[cite: 5]
├── exception/       # Centralized GlobalExceptionHandler[cite: 5]
├── model/           # Category, Question, Option, Quiz, QuizAttempt, User[cite: 5]
├── repository/      # Spring Data JPA Repositories[cite: 5]
└── service/         # Business logic layer (Quiz, Leaderboard, Attempt, etc.)[cite: 5]
