# 🎓 Placement Portal

A production-grade full-stack Placement Portal with Google OAuth2 authentication, JWT-based authorization, and a fully automated CI/CD pipeline using Jenkins and Ansible .

---

## 🚀 Tech Stack

| Layer | Technology |
|-------|-----------|
| Frontend | React.js (Vite), Axios |
| Backend | Spring Boot, Spring Security, Spring Data JPA |
| Database | MySQL (Dockerized) |
| Auth | Google OAuth2 + JWT |
| Web Server | Nginx (Reverse Proxy + SPA) |
| DevOps | Docker, Jenkins, Ansible |

---

## 🏗️ Architecture

```
Browser
   │
   ▼
Nginx :80
   ├── /api/*  ──► Spring Boot Backend :8080
   └── /*      ──► React SPA (index.html)

Docker Network: placement-net
   ├── frontend  (React + Vite + Nginx)
   ├── backend   (Spring Boot)
   └── mysql     (MySQL 8 + named volume)
```

---

## ✨ Features

- 🔐 **Google OAuth2** login with JWT-based stateless session management
- 🛡️ **Spring Security** with custom JWT filter and Role-Based Access Control (RBAC)
- 👥 **Role-specific dashboards** for Students, Companies, and Admins
- 📋 **Job posting, application submission**, and application status tracking
- 🔄 **Axios interceptors** to auto-attach JWT Bearer tokens to all API requests
- 🐳 **Dockerized** MySQL with persistent named volumes
- ⚙️ **Nginx** reverse proxy with SPA fallback via `try_files`
- 🤖 **Automated CI/CD** via Jenkins + Ansible

---

## 🔐 Authentication Flow

```
User clicks "Login with Google"
        │
        ▼
Google OAuth2 Consent Screen
        │
        ▼
Backend receives OAuth2 callback
        │
        ▼
Backend generates signed JWT
        │
        ▼
Frontend stores JWT, attaches to every request via Axios interceptor
        │
        ▼
Spring Security JWT filter validates token on every API call
        │
        ▼
RBAC enforces role-based endpoint access
```

---

## 🐳 Docker Setup

### Services

| Container | Image | Port |
|-----------|-------|------|
| frontend | placement-frontend | 80 |
| backend | placement-backend | 9090 → 8080 |
| mysql | mysql:8 | - (internal) |

### Run Manually

```bash
# Create network and volume
docker network create placement-net
docker volume create mysql-data

# MySQL
docker run -d --name mysql \
  --network placement-net \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=placement \
  -v mysql-data:/var/lib/mysql \
  mysql:8

# Backend
docker run -d --name backend \
  --network placement-net \
  -p 9090:8080 \
  -e SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID=<your_client_id> \
  -e SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET=<your_client_secret> \
  placement-backend

# Frontend
docker run -d --name frontend \
  --network placement-net \
  -p 80:80 \
  placement-frontend
```

---

## ⚙️ CI/CD Pipeline

```
Developer pushes to GitHub (main branch)
        │
        ▼
Jenkins detects change (webhook / poll)
        │
        ▼
Stage 1: Clone Repository
Stage 2: Build Backend (Maven)
Stage 3: Build Backend Docker Image
Stage 4: Build Frontend Docker Image
Stage 5: Deploy via Ansible
        │
        ▼
Ansible playbook:
  - Stops old containers
  - Creates Docker network & volume
  - Starts MySQL → waits 15s
  - Starts Backend (injects OAuth secrets)
  - Starts Frontend
```

> 🔒 OAuth2 credentials are stored in **Jenkins Credentials Store** and injected at runtime via `--extra-vars`. They are never stored in source code or version history.

---

## 📁 Project Structure

```
Spring_Boot/
├── portal/                        # Spring Boot Backend
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── placement-frontend/            # React Frontend
│   ├── src/
│   ├── nginx.conf
│   └── Dockerfile
├── devops-ansible/                # Ansible
│   ├── deploy.yml
│   └── inventory.ini
└── Jenkinsfile                    # CI/CD Pipeline
```

---

## 🔧 Local Development Setup

### Prerequisites
- Java 17+
- Node.js 18+
- Maven
- Docker
- Google OAuth2 credentials

### Backend
```bash
cd portal
mvn clean package -DskipTests
mvn spring-boot:run
```

### Frontend
```bash
cd placement-frontend
npm install
npm run dev
```

### Environment Variables (Backend)
```properties
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID=your_client_id
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET=your_client_secret
```

---

## 📜 Nginx Configuration

```nginx
server {
    listen 80;
    root /usr/share/nginx/html;

    location /api/ {
        proxy_pass http://backend:8080/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    location / {
        try_files $uri /index.html;
    }
}
```

---

## 👤 Author

**Aditya Pareek**  
[GitHub](https://github.com/Aditya01237) • [LinkedIn](https://linkedin.com/in/pareekaditya)
