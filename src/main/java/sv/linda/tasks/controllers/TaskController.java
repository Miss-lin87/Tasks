package sv.linda.tasks.controllers;

import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sv.linda.tasks.constructors.ModelView;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.constructors.TaskDAO;
import sv.linda.tasks.constructors.Tasks;
import sv.linda.tasks.enums.Status;
import sv.linda.tasks.functions.saveTask;
import sv.linda.tasks.validation.TaskValidator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.logging.FileHandler;

@RestController
public class TaskController implements WebMvcConfigurer {
    private final ModelView View = new ModelView();
    private final TaskDAO taskDAO;
    private final TaskValidator valid;

    @Autowired
    public TaskController(TaskDAO taskDAO, TaskValidator valid) {
        this.taskDAO = taskDAO;
        this.valid = valid;
    }

    @GetMapping("/all")
    private Tasks getTasks() {
        return taskDAO.getTasks();
    }

    @GetMapping("/{name}")
    private Task getTask(@PathVariable String name) {
        for (Task task : taskDAO.getTasks().getTaskList()) {
            if (task.getTitle().equals(name)) {
                System.out.println(task.getTitle());
                return task;
            }
        }
        return null;
    }
    
    @GetMapping("/addTask")
    private ModelAndView loadPage(Task task, Errors errors) {
        return View.page("addTask");
    }

    @PostMapping("/Testing")
    public ResponseEntity<?> createTask(Task task) {
        Errors errors = new BeanPropertyBindingResult(task, "task");
        valid.validate(task, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        return ResponseEntity.ok("user created");
    }

    @PostMapping("/addTask")
    public ModelAndView newTask(Task task, BindingResult bind) throws IOException {
        Errors errors = new BeanPropertyBindingResult(task, "task");
        valid.validate(task, errors);
        ModelAndView view = new ModelAndView("addTask");
        if (errors.hasErrors()) {
            String nameerror = (errors.getFieldError("title") == null) ? "" : Objects.requireNonNull(errors.getFieldError("title")).getDefaultMessage();
            String descriptionError = (errors.getFieldError("description") == null) ? "" : Objects.requireNonNull(errors.getFieldError("description")).getDefaultMessage();
            view.addObject("nameError", nameerror);
            view.addObject("descriptionError", descriptionError);
            return view;
        } else {
            saveTask save = new saveTask();
            save.save(task);
            taskDAO.addTask(task);
        }
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/")
    public ModelAndView testPage() {
        ModelAndView view = View.page("tasksPage");
        view.addObject("tasks", taskDAO.getTasks().getTaskList());
        view.addObject("Statuses", Status.getAll());
        return view;
    }

    @PostMapping("/updateTask")
    public ModelAndView updateTask(HttpServletRequest request) throws IOException {
        for (Task task : taskDAO.getTasks().getTaskList()) {
            task.changeStatus(Status.toEnum(request.getParameter("Selected" + task.getTitle())));
            new saveTask().save(task);
        }
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/deleteTask/{name}")
    public ModelAndView deleteTask(@PathVariable String name) throws IOException {
        taskDAO.getTasks().getTaskList().removeIf(task -> task.getTitle().equals(name));
        String path = "src/main/java/sv/linda/tasks/savedTasks/" + name + ".json";
        new File(path).delete();
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/adding")
    public ResponseEntity<Object> addTasks(@RequestParam Task task) {
        taskDAO.addTask(task);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(task)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}