FROM eclipse-temurin:21-jre-alpine
RUN adduser -D appuser
VOLUME /tmp
COPY applications/app-service/build/libs/*.jar Authentication.jar
ENV JAVA_OPTS=" -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
# Replace with a non-root user to avoid running the container with excessive privileges
USER appuser
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar Authentication.jar --spring.profiles.active=docker"]
