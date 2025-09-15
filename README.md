# Microservicios Castañeda

Sistema de microservicios financieros con Spring Boot y Docker.

## ⚡ Inicio Rápido

### Requisitos
- Java 17+
- Maven 3.8+
- Docker & Docker Compose

### 1.- Arrancar el Proyecto

```bash
# Construir JARs de todos los microservicios
mvn clean package -DskipTests

# Construir y levantar todos los servicios con Docker
docker-compose up --build -d
```

Docker Compose automáticamente:
- Buildea cada Dockerfile de cada microservicio
- Levanta todos los contenedores
- Configura la red entre servicios
- Inicializa PostgreSQL

### 2.- Verificar que todo funciona
```bash
# Ver estado de todos los servicios
docker-compose ps

# Ver logs en tiempo real
docker-compose logs -f
```

## 🌐 Servicios Disponibles

| Servicio | URL | Puerto |
|----------|-----|--------|
| **Customers** | http://localhost:8083 | 8083 |
| **Finance Products** | http://localhost:8082 | 8082 |
| **BFF Web** | http://localhost:8030 | 8030 |
| **BFF Mobile** | http://localhost:8031 | 8031 |
| **Security Service** | http://localhost:9000 | 9000 |
| **PostgreSQL** | localhost:5433 | 5433 |

## 📋 Comandos Útiles

```bash
# Parar todos los servicios
docker-compose down

# Reiniciar un servicio específico
docker-compose restart mcs-customers

# Reconstruir solo un servicio
docker-compose up --build mcs-customers

# Ver logs de un servicio específico
docker-compose logs -f mcs-customers
```

## 🔧 Si algo falla

1. **Puerto ocupado:** Cambiar puertos en `docker-compose.yml`
2. **Error de memoria:** Reiniciar Docker
3. **Base de datos:** `docker-compose restart postgres`
4. **JAR no encontrado:** Ejecutar `mvn clean package -DskipTests` primero

## 📚 API Docs
- Swagger UI disponible en cada servicio: `http://localhost:{puerto}/swagger-ui.html`

---
**¡Listo para desarrollar!** 🚀