FROM openjdk:17-jdk

WORKDIR /app

ENV TZ Asia/Seoul

EXPOSE 8080

COPY build/libs/hot-deal-0.0.1-SNAPSHOT.jar /app

CMD ["java", "-jar", "hot-deal-0.0.1-SNAPSHOT.jar"]