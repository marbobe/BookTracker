# Paso 1: Usar una imagen de Java 21
FROM eclipse-temurin:21-jdk-jammy

# Paso 2: Directorio de trabajo dentro del contenedor
WORKDIR /app

# Paso 3: Copiar el archivo JAR generado por Maven al contenedor
# Nota: Primero debes hacer 'mvn clean package' en tu PC
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]