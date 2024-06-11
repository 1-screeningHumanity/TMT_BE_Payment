FROM openjdk:17
ENV TZ=Asia/Seoul
COPY build/libs/TMT-BE-PaymentServer-0.0.1.jar PaymentServer.jar
ENTRYPOINT ["java", "-jar", "PaymentServer.jar"]