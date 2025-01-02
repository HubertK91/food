FROM maven:3.9.9-amazoncorretto-23-debian-bookworm AS MAVEN_BUILD
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn package

FROM openjdk:25-slim-bookworm
EXPOSE 8080
COPY --from=MAVEN_BUILD /target/food-*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]