package sv.linda.tasks.controllers;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "page")
public class PageConfig {
    private String login;
    private String tasks;
    private String error;
    private add add = new add();

    @Data
    public static class add {
         private String login;
         private String task;
     }
}
