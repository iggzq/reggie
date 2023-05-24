FROM openjdk:21-slim
ADD /target/reggie-1.0-SNAPSHOT.jar ./
RUN mkdir -p /ReggieLog
CMD ["java", "-Dlogging.file=/ReggieLog/Reggie.log", "-jar", "reggie-1.0-SNAPSHOT.jar"]
EXPOSE 2001
VOLUME ["/ReggieLog"]




FROM mysql:8.0.26
EXPOSE 3306
ENV MYSQL_ROOT_PASSWORD=your_password

