FROM maven:3.6.3-jdk-11 as build

WORKDIR /opt/cassandra-keyspaces

COPY src ./src
COPY pom.xml ./pom.xml

RUN mvn package -Dmaven.test.skip=true


FROM openjdk:11.0.8-jre

WORKDIR /opt/cassandra-keyspaces

COPY --from=build /opt/cassandra-keyspaces/target/cassandra-keyspaces-0.0.1.jar ./

ENTRYPOINT ["java", "-jar", "cassandra-keyspaces-0.0.1.jar"]

EXPOSE 8080