FROM openjdk:11-jdk-slim as builder
WORKDIR /app
COPY . /app
RUN ./gradlew clean build

# Execute container as user.
FROM openjdk:11-jdk-slim
LABEL maintainer=g.nespolino@gmail.com
USER 1001
COPY --from=builder /app/build/libs/lode.jar /lode.jar
CMD ["java", "-jar", "/lode.jar"]
EXPOSE 8080 8009
