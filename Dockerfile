FROM openjdk:11-jre-alpine
COPY ./target/todoist-1.0.0-SNAPSHOT.jar /usr/src/todoist/
WORKDIR /usr/src/todoist
CMD ["java", "-jar", "todoist-1.0.0-SNAPSHOT.jar"]