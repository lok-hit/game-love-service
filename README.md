
# Game Love Service

Track which games players love — built with Spring Boot, Kafka, Keycloak, Prometheus, and Grafana.

## 🚀 How to Run

```bash
docker-compose up --build

🔐 Keycloak
• 	URL:
• 	Admin:
• 	Realm:
• 	Client:
📊 Monitoring
• 	Prometheus:
• 	Grafana:
• 	Login:
• 	Add Prometheus as data source:
• 	Import Kafka dashboard: ID
• 	Import JVM dashboard: ID
📦 API
• 	Swagger UI:
• 	Secured endpoints require JWT from Keycloak
📁 Project Structure
• 	 — Spring Boot backend
• 	 — Vue 3 SPA with Keycloak integration
• 	 — Prometheus config
• 	 — Full stack orchestration
🧪 Tests
• 	Unit: JUnit
• 	Integration: Testcontainers
• 	Contract: Pact
• 	BDD: Cucumber
🧠 Author
Mateusz @ Lokhit


jvm config:
java \
  -Xms512m \
  -Xmx2048m \
  -Xss512k \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -XX:+PrintGCDetails \
  -XX:+PrintGCTimeStamps \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=./heapdump.hprof \
  -XX:+ExitOnOutOfMemoryError \
  -XX:+UseStringDeduplication \
  -XX:+AlwaysPreTouch \
  -XX:+DisableExplicitGC \
  -Dspring.profiles.active=prod \
  -Djava.security.egd=file:/dev/./urandom \
  -jar game-love-service.jar
