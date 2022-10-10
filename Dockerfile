FROM openjdk:19-slim

ARG JAVA_OPTS

ENV APP_NAME=HttpApplication
ARG APP_HOME=/opt/app

RUN groupadd app && useradd -d ${APP_HOME} -s /bin/nologin -g app app
WORKDIR ${APP_HOME}
COPY out/artifacts/empty_jar/empty.jar ${APP_NAME}.jar
EXPOSE 8080

USER app
ENTRYPOINT java --enable-preview \
-XX:MaxRAMPercentage=100 -XX:MinRAMPercentage=100 \
${JAVA_OPTS} \
-jar ${APP_NAME}.jar
