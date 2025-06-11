package sv.linda.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sv.linda.tasks.database.DataBaseFunctions;

@SpringBootApplication
public class TasksApplication {
	public static final DataBaseFunctions database = new DataBaseFunctions("mongodb://localhost:27017/", "Tasks");

	public static void main(String[] args) {
		SpringApplication.run(TasksApplication.class, args);
	}
}