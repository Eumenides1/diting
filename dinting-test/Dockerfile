# 使用官方的 Java 17 JDK 镜像
FROM openjdk:17-jdk-slim

# 安装 Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 设置工作目录
WORKDIR /app

# 复制 Maven 项目文件
COPY pom.xml .
COPY src ./src

# 构建项目
RUN mvn install -DskipTests

# 暴露应用程序运行的端口
EXPOSE 8888

# 运行应用程序
ENTRYPOINT ["java", "-jar", "target/dinting-test-0.0.1.jar"]