FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

# 환경변수 추가
ARG MODULE_PATH=cercat-application/app-api
ARG MODULE_NAME=app-api
ARG VERSION=0.0.2

COPY ../.. .

RUN ./gradlew :cercat-application:${MODULE_NAME}:build -x test
RUN mkdir -p ${MODULE_PATH}/build/extracted && (java -Djarmode=layertools -jar ${MODULE_PATH}/build/libs/${MODULE_NAME}-${VERSION}.jar extract --destination ${MODULE_PATH}/build/extracted)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp

# 환경변수를 이용해 경로 설정
ARG MODULE_PATH=cercat-application/app-api
ARG MODULE_NAME=app-api
ARG VERSION=0.0.2
ARG EXTRACTED=/workspace/app/${MODULE_PATH}/build/extracted

COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]
