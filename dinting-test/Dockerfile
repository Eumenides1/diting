# 使用官方的 Java 17 JDK 镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制 Maven 项目文件
COPY pom.xml .
COPY src ./src

# 构建项目
RUN mvn install -DskipTests

# 将构建好的 JAR 文件复制到工作目录
RUN cp target/dinting-test-*.jar app.jar

# 暴露应用程序运行的端口
EXPOSE 8888

# 运行应用程序
ENTRYPOINT ["java", "-jar", "app.jar"]