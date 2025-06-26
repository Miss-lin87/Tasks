package sv.linda.tasks.database;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "db")
public class DatabaseConfig {
    private String url;
    private Database database = new Database();

    @Data
    public static class Database {
        private String name;
        private String tasks;
        private String users;
    }
}