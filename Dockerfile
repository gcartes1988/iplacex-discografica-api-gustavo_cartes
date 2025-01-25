# Primera etapa: Construcción con Gradle
FROM gradle:jdk21 AS builder

WORKDIR /app

# Copiar archivos Gradle necesarios
COPY ./build.gradle .
COPY ./settings.gradle .

# Copiar el código fuente
COPY src ./src

# Usar Gradle para construir el archivo JAR
RUN gradle build --no-daemon

# Segunda etapa: Ejecución con OpenJDK
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copiar el archivo JAR generado desde la etapa anterior
COPY --from=builder /app/build/libs/*.jar discografia-1.jar

# Exponer el puerto 443 
EXPOSE 443

# Ejecutar la aplicación
CMD ["java", "-jar", "discografia-1.jar"]
