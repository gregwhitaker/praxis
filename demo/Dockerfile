FROM adoptopenjdk/openjdk11
ARG JAR_FILE
VOLUME /tmp
RUN mkdir -p /opt/demo
COPY build/libs/${JAR_FILE} /opt/demo/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/demo/app.jar"]
