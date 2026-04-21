# WorkCore Backend

Backend API for **WorkCore**, a full-stack productivity web application designed to help users manage tasks, accounts, and workflow securely.

Built with **Java Spring Boot**, **PostgreSQL**, **Supabase**, and deployed on **Render**.

---

## Live API

Backend URL: https://workcore-api.onrender.com

---

## Features

### Authentication & Security

* User registration and login
* Google OAuth2 authentication
* JWT token generation and validation
* Session-based authentication support
* Secure logout functionality

### User Management

* View user profile
* Update profile information
* Change password
* Delete account

### Password Recovery

* Forgot password email flow
* Secure password reset tokens

### Task Management

* Create tasks
* View all user tasks
* Update tasks
* Delete tasks

### Contact System

* Contact form email integration

---

## Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* REST API
* Maven

### Database

* PostgreSQL
* Supabase

### Authentication

* Google OAuth2
* JWT

### Deployment

* Render

---

## Project Architecture

Frontend (Netlify) communicates with Spring Boot REST API hosted on Render.

API handles:

Frontend → Controllers → Services → Repositories → PostgreSQL Database

---

## API Endpoints

## Authentication

| Method | Endpoint                  |
| ------ | ------------------------- |
| POST   | /api/auth/register        |
| POST   | /api/auth/login           |
| GET    | /api/auth/me              |
| POST   | /api/auth/logout          |
| POST   | /api/auth/forgot-password |
| POST   | /api/auth/reset-password  |

---

## User

| Method | Endpoint           |
| ------ | ------------------ |
| GET    | /api/user/profile  |
| PUT    | /api/user/profile  |
| PUT    | /api/user/password |
| DELETE | /api/user/account  |

---

## Tasks

| Method | Endpoint        |
| ------ | --------------- |
| GET    | /api/tasks      |
| POST   | /api/tasks      |
| PUT    | /api/tasks/{id} |
| DELETE | /api/tasks/{id} |

---

## Contact

| Method | Endpoint     |
| ------ | ------------ |
| POST   | /api/contact |

---

## Environment Variables

Create a `.env` or Render environment configuration:

```env id="c71ae"
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=

JWT_SECRET=

GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=

EMAIL_USERNAME=
EMAIL_PASSWORD=
```

---

## Run Locally

```bash id="6d8pp"
git clone <repo-url>
cd workcore-backend
mvn spring-boot:run
```

---

## Why I Built This

I created WorkCore Backend to gain hands-on experience building production-style backend systems using Java Spring Boot, authentication flows, database integration, REST APIs, and cloud deployment.

---

## What I Learned

* Backend architecture using Spring Boot
* Authentication with Google OAuth2 + JWT
* PostgreSQL relational database design
* Secure user session handling
* Deployment and environment configuration
* RESTful API development

---

## Future Improvements

* Role-based authorization
* Rate limiting
* Unit/integration testing
* Docker containerization
* CI/CD pipeline
* Task categories and due dates
* Multi-user collaboration

---

## Author

Davon Appolon
GitHub: https://github.com/DavonApp
