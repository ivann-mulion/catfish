FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Копируем JAR из текущей папки target
COPY target/*.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]