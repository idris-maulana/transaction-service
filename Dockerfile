FROM openjdk:21
EXPOSE 8080
COPY target/transaction-service-0.0.1-SNAPSHOT.jar /usr/src/app/transaction-service-0.0.1-SNAPSHOT.jar
WORKDIR /usr/src/app
CMD [ "java","-jar","transaction-service-0.0.1-SNAPSHOT.jar" ]