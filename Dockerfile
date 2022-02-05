FROM openjdk:11
MAINTAINER Liping
COPY ./target/mediscreen-record-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app.jar"]