# ✨ TravelBank App 🚀

A full-stack **Banking Application** integrating a secure Spring Boot backend with a modern frontend — **TravelBank**.  
Built with robust security using **Spring Security**, **Keycloak**, **OpenID Connect**, and more!

---

## 🔐 Security Stack

| Feature                         | Description |
|--------------------------------|-------------|
| 🛡️ **Spring Security**         | Core security for all APIs |
| 🌐 **CORS**                    | Controlled cross-origin requests |
| 🧪 **CSRF Protection**         | CSRF tokens for secure forms |
| 🏷️ **OpenID Connect**          | Modern identity protocol using Keycloak |
| 🔑 **Keycloak Integration**     | Externalized authentication and role-based access |
| 🧩 **Method-Level Security**   | `@PreAuthorize`, `@Secured`, etc. on methods |
| 🧠 **Session Management**      | Fine-tuned session control and policies |

---

## 💼 Functional Modules

The application includes the following business entities:

| Entity         | Description |
|----------------|-------------|
| 💰 **Loans**   | View and manage loan history |
| 🏦 **Accounts** | Account information and activity |
| 💳 **Cards**   | Credit/Debit card details and usage |
| 📊 **Balance** | Real-time account balance retrieval |
| 📬 **Contact** | Submit contact forms or requests |

---

## 🎨 Frontend Features

🔷 Built with a modern UI  
🔐 Protected routes using the Auth token  
📡 Integrates with secured Spring Boot APIs  
📦 SPA ready for Docker deployment

---

## 🔗 Technologies Used

### Backend 🧠
- Java 17
- Spring Boot
- Spring Security
- Spring Web
- Spring Data JPA
- Keycloak (OpenID Connect)
- H2/PostgreSQL (DB)
- Maven

### Frontend 🎨
- Angular (or React — depending on your stack)
- Bootstrap / Tailwind (UI Styling)
- OAuth2 Login integration

---

## 🚀 Getting Started

### 🔧 Prerequisites
- JDK 17+
- Node.js (for frontend)
- Docker (optional for deployment)
- Keycloak server running

### 🛠️ Run Backend

```bash
cd travelbank-backend
./mvnw spring-boot:run
