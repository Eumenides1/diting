# 使用官方的 Java 17 基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 将构建好的 JAR 文件复制到容器中
COPY diting-test/target/dinting-test-*.jar app.jar

# 暴露应用程序运行的端口
EXPOSE 8888

# 运行应用程序
ENTRYPOINT ["java", "-jar", "app.jar"]