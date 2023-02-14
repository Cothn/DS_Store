# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml checkstyle.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-jammy as production
EXPOSE 8080
COPY --from=base /app/target/tradePlace-*.jar /tradePlace.jar
CMD ["java", "-jar", "/tradePlace.jar"]