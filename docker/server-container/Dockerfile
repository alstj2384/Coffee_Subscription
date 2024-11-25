FROM openjdk:17-jdk-slim
ARG JAR_FILE=/docker/server-container/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=dev","/app.jar"]
ENV TZ Asia/Seoul

#-Dspring.profiles.active=dev -> 애플리케이션을 '개발(dev)' 설정으로 실행