# Використовуємо офіційний OpenJDK 17 образ
FROM openjdk:17-jdk-slim

# Встановлюємо робочу директорію
WORKDIR /app

# Копіюємо Maven wrapper та pom.xml
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Робимо mvnw виконуваним
RUN chmod +x ./mvnw

# Завантажуємо залежності (для кешування)
RUN ./mvnw dependency:go-offline -B

# Копіюємо вихідний код
COPY src ./src

# Збираємо додаток
RUN ./mvnw clean package -DskipTests

# Запускаємо додаток
EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "target/WebAppsRIVLab4-0.0.1-SNAPSHOT.jar"]
