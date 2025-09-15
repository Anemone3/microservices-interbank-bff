FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar el archivo JAR generado
COPY target/mcs-customers-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto
EXPOSE 8083

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]