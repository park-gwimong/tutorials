FROM openjdk:8-jre
COPY target/Catalogs-1.0.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]