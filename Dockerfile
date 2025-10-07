# -----------------------------
# Etapa 1: Build frontend (Vue)
# -----------------------------
FROM node:20-alpine AS frontend-build

WORKDIR /app/client

COPY client/package*.json ./
RUN npm install

COPY client/ . 
RUN npm run build
# Esto genera /app/client/dist

# -----------------------------
# Etapa 2: Build backend (Spring Boot)
# -----------------------------
FROM eclipse-temurin:21-jdk-alpine AS backend-build
WORKDIR /app

COPY server/ /app/server
COPY --from=frontend-build /app/client/dist /app/server/src/main/resources/static

WORKDIR /app/server
RUN chmod +x mvnw

# Si no existe application.properties, copiamos el sample
RUN if [ ! -f "src/main/resources/application.properties" ]; then \
      cp src/main/resources/application.properties.sample src/main/resources/application.properties; \
    fi

RUN ./mvnw clean package -DskipTests

# -----------------------------
# Etapa 3: Imagen final
# -----------------------------
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=backend-build /app/server/target/server-0.0.1-SNAPSHOT.war server-0.0.1-SNAPSHOT.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "server-0.0.1-SNAPSHOT.war"]
