package sv.linda.tasks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.constructors.TaskDAO;
import sv.linda.tasks.constructors.Tasks;
import sv.linda.tasks.enums.Status;
import sv.linda.tasks.functions.saveTask;

import javax.naming.Binding;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

@RestController
public class TaskController implements WebMvcConfigurer {
    private final TaskDAO taskDAO;

    @Autowired
    public TaskController(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @GetMapping("/all")
    public Tasks getTasks() {
        return taskDAO.getTasks();
    }

    @GetMapping("/{name}")
    public Task getTask(@PathVariable String name) {
        for (Task task : taskDAO.getTasks().getTaskList()) {
            if (task.getTitle().equals(name)) {
                return task;
            }
        }
        return null;
    }
    
    @GetMapping("/addTask")
    public ModelAndView loadPage(Task task) {
        ModelAndView view = new ModelAndView();
        view.setViewName("addTask");
        return view;
    }

    @PostMapping("/addTask")
    public String newTask(Task task, BindingResult bind) throws IOException {
        saveTask save = new saveTask();
        save.save(task);
        taskDAO.addTask(task);
        return "Task added: " + task.getTitle();
    }

    @GetMapping("/")
    public ModelAndView testPage() {
        ModelAndView view = new ModelAndView();
        view.setViewName("tasksPage");
        view.addObject("tasks", taskDAO.getTasks().getTaskList());;
        view.addObject("Statuses", Status.getAll());
        return view;
    }

    @PostMapping("/updateTask")
    public void updateTask(Tasks tasks, BindingResult bind) throws IOException {
        System.out.println("Its working");
    }

    //@GetMapping("/newTask/{name}/{description}")
    //public Tasks newTask(@PathVariable String name, @PathVariable String description) {
    //    Task task = new Task(name, description);
    //    taskDAO.addTask(task);
    //    return taskDAO.getTasks();
    //}

    @PostMapping("/add")
    public ResponseEntity<Object> addTask(@RequestBody String name, String description) {
        Task task = new Task(name, description);
        taskDAO.addTask(task);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(task)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/adding")
    public ResponseEntity<Object> addTasks(@RequestParam Task task) {
        taskDAO.addTask(task);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(task)
                .toUri();
        System.out.println("{/name}");
        return ResponseEntity.created(location).build();
    }
}
