FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

ARG MODULE_PATH=applications/app-alarm
ARG MODULE_NAME=app-alarm
ARG VERSION=0.0.1

COPY ../.. .

RUN ./gradlew :applications:${MODULE_NAME}:build -x test --parallel
RUN mkdir -p ${MODULE_PATH}/build/extracted && (java -Djarmode=layertools -jar ${MODULE_PATH}/build/libs/${MODULE_NAME}-${VERSION}.jar extract --destination ${MODULE_PATH}/build/extracted)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp

ARG MODULE_PATH=applications/app-alarm
ARG MODULE_NAME=app-alarm
ARG VERSION=0.0.1
ARG EXTRACTED=/workspace/app/${MODULE_PATH}/build/extracted

COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "org.springframework.boot.loader.launch.JarLauncher"]
