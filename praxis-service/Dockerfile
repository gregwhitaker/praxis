FROM adoptopenjdk/openjdk11
ARG JAR_FILE
VOLUME /tmp
RUN mkdir -p /opt/praxis-service
COPY build/libs/${JAR_FILE} /opt/praxis-service/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/praxis-service/app.jar"]
