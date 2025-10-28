# 🐹 Fidelín Clicker – Backend

**Fidelín Clicker** es un proyecto meme convertido en app real 🎮  
Un **clicker game** donde los usuarios registran su cuenta, inician sesión y compiten por ver quién da más clics a la imagen del mítico **Fidelín** 🐹.  

Este repositorio contiene el **backend REST API**, desarrollado en **Spring Boot 3 (Java 17)** con **JWT**, **Spring Security**, y **H2** como base de datos embebida.

---

## 🚀 Características principales

- 🧍‍♂️ Registro y login de usuarios (`/auth/register`, `/auth/login`)
- 🔐 Autenticación JWT con expiración configurable
- 🖱️ Incremento de puntuación (`/score/add`)
- 🏆 Leaderboard público (`/leaderboard/top`)
- 🧠 Sistema anticheat con limitación de clicks por tiempo
- 🗄️ Base de datos H2 embebida (modo dev)
- 🐳 Configuración Docker lista para producción

---

## 🧩 Tecnologías

| Componente | Tecnología |
|-------------|-------------|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3.3 |
| Seguridad | Spring Security + JWT |
| Persistencia | Spring Data JPA + H2 |
| Build | Maven Wrapper (`mvnw`) |
| Entorno | Docker / Dev local |
| API Testing | Postman / cURL / Frontend Android |

---

## ⚙️ Requisitos previos

- ☕ **Java 17** instalado  
- 🐘 **Maven** (opcional, el proyecto incluye `mvnw`)  
- 🐳 (Opcional) **Docker** si quieres levantarlo en contenedor

---

## 🏁 Cómo ejecutar

### ▶️ Modo local
```bash
# Clonar el repo
git clone https://github.com/tuusuario/fidelin-clicker-backend.git
cd fidelin-clicker-backend

# Ejecutar con Maven Wrapper
.\mvnw.cmd spring-boot:run
