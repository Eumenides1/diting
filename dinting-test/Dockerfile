# 第一阶段：使用 Maven 镜像构建项目
FROM maven:3.8.6-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn install -DskipTests

# 第二阶段：使用 JDK 镜像运行应用程序
FROM openjdk:17-jdk-slim
WORKDIR /app
# 从第一阶段复制构建好的 JAR 文件
COPY --from=builder /app/target/dinting-test-*.jar app.jar
# 暴露应用程序运行的端口
EXPOSE 8888
# 运行应用程序
ENTRYPOINT ["java", "-jar", "app.jar"]