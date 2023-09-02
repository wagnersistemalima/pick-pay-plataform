FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 8080
ADD ./build/libs/pickPayPlataform-0.0.1-SNAPSHOT.jar pickPayPlataform-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/pickPayPlataform-0.0.1-SNAPSHOT.jar"]