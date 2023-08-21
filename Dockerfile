FROM eclipse-temurin:11-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar  app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
