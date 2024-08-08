FROM eclipse-temurin:21-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8090

# TODO: resolve ptoblem with layerd app!
#FROM eclipse-temurin:21-jdk-alpine as builder
#VOLUME /tmp
#COPY target/app.jar app.jar
#RUN java -Djarmode=layertools -jar app.jar extract --destination extracted
#
#FROM eclipse-temurin:21-jre
#VOLUME /tmp
#COPY --from=builder extracted/dependencies/ ./
#COPY --from=builder extracted/spring-boot-loader/ ./
#COPY --from=builder extracted/snapshot-dependencies/ ./
#COPY --from=builder extracted/application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
#EXPOSE 8090