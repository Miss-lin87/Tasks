package sv.linda.tasks.controllers;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sv.linda.tasks.constructors.Login.LoginDAO;
import sv.linda.tasks.constructors.Task.TaskDAO;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.database.DatabaseConfig;
import sv.linda.tasks.functions.Converter;
import sv.linda.tasks.validation.CreateLoginValidator;
import sv.linda.tasks.validation.LoginValidator;
import sv.linda.tasks.validation.TaskValidator;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    public void init(Gson gson) {
        Converter convert = new Converter(gson);
    }

    @Bean
    public TaskValidator taskValidator(TaskDAO taskDAO) {
        return new TaskValidator(taskDAO);
    }

    @Bean
    public LoginValidator loginValidator(LoginDAO loginDAO) {
        return new LoginValidator(loginDAO);
    }

    @Bean
    public CreateLoginValidator createLoginValidator(LoginDAO loginDAO) {
        return new CreateLoginValidator(loginDAO);
    }

    @Bean
    public PageConfig pageConfig() {
        return new PageConfig();
    }

    @Bean
    public ViewPages viewPages(PageConfig pageConfig) {
        return new ViewPages(pageConfig);
    }

    @Bean
    public MongoClient mongoClient(DatabaseConfig dbConfig) {
        return MongoClients.create(dbConfig.getUrl());
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient, DatabaseConfig dbConfig) {
        return mongoClient.getDatabase(dbConfig.getDatabase().getName());
    }

    @Bean
    public DataBaseFunctions database(MongoClient mongoClient, MongoDatabase mongoDB) {
        return new DataBaseFunctions(mongoClient, mongoDB);
    }
}