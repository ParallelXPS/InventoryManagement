FROM azul/zulu-openjdk-alpine:8

EXPOSE 9000 9001
ENV JAVA_TOOL_OPTIONS="-Djava.security.egd=file:/dev/./urandom -XX:+ExitOnOutOfMemoryError -javaagent:/newrelic/newrelic-agent-3.43.0.jar"

COPY newrelic /newrelic
COPY build/libs/*.jar /app
RUN chown -R nobody: /newrelic /app
USER nobody

CMD ["java", "-jar", "/app"]
