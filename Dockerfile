FROM openjdk:21-slim
ADD /target/reggie-1.0-SNAPSHOT.jar ./
RUN mkdir -p /ReggieLog
CMD ["java", "-Dlogging.file=/ReggieLog/Reggie.log", "-jar", "reggie-1.0-SNAPSHOT.jar"]
EXPOSE 2001
VOLUME ["/ReggieLog"]

