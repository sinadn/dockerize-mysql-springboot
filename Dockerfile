FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} dockerTutorial.jar
EXPOSE 8010
ENTRYPOINT ["java","-jar","/dockerTutorial.jar"]