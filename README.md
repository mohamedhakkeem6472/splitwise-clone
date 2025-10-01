# Splitwise Clone Backend

## Description
A backend system for sharing expenses within groups. Users can log in with Google OAuth2, create groups, add expenses, and settle balances.

---

## Features
- Google OAuth2 login
- Role-based security (USER, ADMIN)
- Create and manage groups
- Add expenses (equal or custom split)
- Maintain per-user balance sheets
- Settle payments transactionally
- H2 in-memory database

---

## Technologies
- Java 17
- Spring Boot 3.x
- Spring Security + OAuth2
- Spring Data JPA
- H2 Database
- Lombok

---

## Getting Started

```bash
1. Clone Repository
git clone <YOUR_REPO_URL>
cd splitwise
2. Configure Google OAuth2

Edit src/main/resources/application.yaml:

Replace YOUR_GOOGLE_CLIENT_ID and YOUR_GOOGLE_CLIENT_SECRET

3. Run Application
mvn clean spring-boot:run

4. Access H2 Console

URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:splitwise-db

User: sa

Password: (empty)

API Endpoints (Sample)
Groups

POST /groups - Create group

GET /groups/{id} - Get group details

Expenses

POST /expenses - Add expense

GET /expenses/{id} - Get expense details

Settlements

POST /settlements - Settle payment
git clone <YOUR_REPO_URL>
cd splitwise
