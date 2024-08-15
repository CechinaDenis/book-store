FROM eclipse-temurin:21-jdk-alpine AS builder
VOLUME /tmp
COPY target/app.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract --destination extracted

FROM eclipse-temurin:21-jre
VOLUME /tmp
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./
ENTRYPOINT ["java", "-Dspring.profiles.active=dockerd", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8090