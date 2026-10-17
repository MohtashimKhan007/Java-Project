<div align="center">

# 🧠 Online Quiz & Assessment System

A production-grade, enterprise-ready assessment platform engineered for scale, reliability, and security.

[![Java](https://img.shields.io/badge/Java-17-orange.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring_Security-Auth-green.svg?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Flyway](https://img.shields.io/badge/Flyway-Migrations-red.svg?style=for-the-badge&logo=flyway&logoColor=white)](https://flywaydb.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED.svg?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

</div>

---

## 📌 Executive Overview

The **Online Quiz & Assessment System** is a modular backend architecture built with **Java 17** and **Spring Boot**. It streamlines dynamic quiz management, real-time submission evaluation, automated scoring, and global competitive leaderboards. Designed with defensive backend engineering principles, the system leverages custom role authorization filters, centralized exception management, database version control via Flyway, and containerized deployment readiness.

---

## ✨ Core Features & Highlights

* 🔐 **Granular Role-Based Access Control:** Distinct privilege layers separating administrative operations (quiz/question creation) from candidate assessment flows via custom `RoleAuthFilter` and `SecurityConfig`.
* 🎯 **Dynamic Quiz Categorization:** Flexible entity mapping pairing multiple-choice questions and options with dynamic topic categories.
* ⚡ **Automated Attempt Grading:** Sub-second answer evaluation via `QuizAttemptService` that calculates dynamic test metrics and scores.
* 🏆 **Real-Time Global Leaderboard:** Instantaneous ranking aggregation computed via dedicated `LeaderboardService`.
* 🗄️ **Zero-Downtime Database Versioning:** Schema evolutions maintained through structured Flyway migrations (`V1__init_schema.sql`, `V2__create_question_and_option_tables.sql`).
* 🛡️ **Defensive Architecture:** Strict DTO input validation with unified REST responses handled by a centralized `GlobalExceptionHandler`.
* 🐳 **Cloud & Container Ready:** Bundled multi-stage `Dockerfile` tuned for quick deployments on cloud environments such as Render[cite: 4, 5].

---

## 🛠️ Architecture & Tech Stack

| Layer | Technologies |
|---|---|
| **Core Framework** | Java 17, Spring Boot, Spring Data JPA, Spring Security |
| **Data Persistence** | MySQL 8, Flyway DB Migrations |
| **Client Interface** | Static HTML5 / Modern CSS & JavaScript[cite: 5] |
| **Tooling & DevOps** | Docker, Apache Maven (`mvnw`), Git[cite: 4, 5] |
| **Architecture Style** | Layered Controller-Service-Repository Pattern, DTO Decoupling[cite: 5] |

---

## 📂 Project Structure

```text
src/main/java/quiz/
├── config/              # RoleAuthFilter, SecurityConfig (RBAC Layer)[cite: 5]
├── controller/          # REST Controllers (Quiz, Attempt, Leaderboard, Question)[cite: 5]
├── dto/                 # Request & Response Data Transfer Objects[cite: 5]
├── exception/           # Centralized GlobalExceptionHandler & Error models[cite: 5]
├── model/               # JPA Entities (User, Category, Question, Option, QuizAttempt)[cite: 5]
├── repository/          # Spring Data JPA interfaces[cite: 5]
└── service/             # Core business rules & ranking logic[cite: 5]
