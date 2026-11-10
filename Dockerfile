# Stage 1: Build file WAR bằng Maven với JDK 21
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml và toàn bộ mã nguồn
COPY pom.xml .
COPY src ./src

# Đóng gói file WAR (bỏ qua test để tăng tốc độ build)
RUN mvn clean package -DskipTests

# Stage 2: Chạy ứng dụng bằng Tomcat 11 trên nền JDK 21
FROM tomcat:11.0-jdk21-temurin

# Xóa toàn bộ ứng dụng mẫu mặc định trong thư mục webapps của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Đổi tên file WAR thành ROOT.war để trang web chạy trực tiếp tại URL gốc (/)
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Mở cổng (Render sẽ tự động ánh xạ cổng qua biến $PORT)
EXPOSE 8080

# Cấu hình tự động đổi cổng Tomcat sang cổng $PORT của Render (nếu không có thì dùng mặc định 8080)
CMD ["sh", "-c", "sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT:-8080}\\\"/g\" /usr/local/tomcat/conf/server.xml && catalina.sh run"]
