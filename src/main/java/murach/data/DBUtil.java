package murach.data;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class DBUtil {
    private static EntityManagerFactory emf;

    public static synchronized EntityManagerFactory getEmFactory() {
        if (emf == null || !emf.isOpen()) {
            Map<String, String> properties = new HashMap<>();

            // 1. Tự động đọc biến môi trường DATABASE_URL từ hosting Render (dạng postgres://user:pass@host:port/db)
            String databaseUrl = System.getenv("DATABASE_URL");
            if (databaseUrl != null && !databaseUrl.isBlank()) {
                try {
                    URI dbUri = new URI(databaseUrl);
                    String userInfo = dbUri.getUserInfo();
                    String username = (userInfo != null) ? userInfo.split(":")[0] : "";
                    String password = (userInfo != null && userInfo.contains(":")) ? userInfo.split(":")[1] : "";
                    int port = (dbUri.getPort() == -1) ? 5432 : dbUri.getPort();
                    String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + port + dbUri.getPath();

                    properties.put("jakarta.persistence.jdbc.url", jdbcUrl);
                    properties.put("jakarta.persistence.jdbc.user", username);
                    properties.put("jakarta.persistence.jdbc.password", password);
                } catch (Exception e) {
                    System.err.println("Không thể parse DATABASE_URL: " + e.getMessage());
                }
            } else {
                // 2. Hoặc đọc qua các biến môi trường riêng biệt nếu có
                String dbUrl = System.getenv("DB_URL");
                String dbUser = System.getenv("DB_USER");
                String dbPassword = System.getenv("DB_PASSWORD");

                if (dbUrl != null) properties.put("jakarta.persistence.jdbc.url", dbUrl);
                if (dbUser != null) properties.put("jakarta.persistence.jdbc.user", dbUser);
                if (dbPassword != null) properties.put("jakarta.persistence.jdbc.password", dbPassword);
            }

            // Nếu không có biến môi trường nào, tự động dùng cấu hình localhost trong persistence.xml
            emf = Persistence.createEntityManagerFactory("surveyPU", properties);
        }
        return emf;
    }

    public static synchronized void closeEmFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
