# Fidelín Clicker Backend (Java 17)

## Run (Windows, no Maven installed)
```powershell
.\mvnw.cmd spring-boot:run
```
macOS/Linux:
```bash
./mvnw spring-boot:run
```

Endpoints:
- POST /auth/register → { token }
- POST /auth/login → { token }
- POST /score/add (Bearer)
- GET /score/me (Bearer)
- GET /leaderboard/top?limit=50

H2 Console: /h2-console
