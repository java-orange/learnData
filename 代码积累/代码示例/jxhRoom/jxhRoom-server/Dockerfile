FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 9003
ENTRYPOINT ["java","-Dspring.profiles.active=mysql","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

