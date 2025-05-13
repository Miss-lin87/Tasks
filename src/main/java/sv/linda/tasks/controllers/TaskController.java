package sv.linda.tasks.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.constructors.TaskDAO;
import sv.linda.tasks.constructors.Tasks;
import sv.linda.tasks.enums.Status;
import sv.linda.tasks.functions.SaveTask;
import sv.linda.tasks.validation.TaskValidator;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@RestController
public class TaskController implements WebMvcConfigurer {
    private final TaskDAO taskDAO;
    private final TaskValidator valid;
    private final String main = "redirect:/";
    private final String SAVE_PATH;

    @Autowired
    public TaskController(TaskDAO taskDAO, TaskValidator valid) {
        this.taskDAO = taskDAO;
        this.valid = valid;
        this.SAVE_PATH = Constants.SavePath();
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
    public ModelAndView loadNewTask(@Valid Task task, Errors errors) {
        return new ModelAndView("addTask");
    }

    @PostMapping("/addTask")
    public ModelAndView newTask(Task task, BindingResult bind) throws IOException {
        Errors errors = new BeanPropertyBindingResult(task, "task");
        valid.validate(task, errors);
        if (errors.hasErrors()) {
            return setErrors(errors);
        } else {
            saveTask(task);
        }
        return new ModelAndView(main);
    }

    @GetMapping("/")
    public ModelAndView mainPage() {
        ModelAndView tempView = new ModelAndView("tasksPage");
        tempView.addObject("tasks", taskDAO.getTasks().getTaskList());
        tempView.addObject("Statuses", Status.getAll());
        return tempView;
    }

    @PostMapping("/updateTask")
    public ModelAndView updateTask(HttpServletRequest request) throws IOException {
        for (Task task : taskDAO.getTasks().getTaskList()) {
            task.changeStatus(Status.toEnum(request.getParameter("Selected" + task.getTitle())));
            new SaveTask(Constants.SavePath()).save(task);
        }
        return new ModelAndView(main);
    }

    @PostMapping("/deleteTask/{name}")
    public ModelAndView deleteTask(@PathVariable String name) throws IOException {
        taskDAO.getTasks().getTaskList().removeIf(task -> task.getTitle().equals(name));
        Path path = Path.of(SAVE_PATH.formatted(name));
        Files.delete(path);
        return new ModelAndView("testing");
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

    private ModelAndView setErrors(Errors errors) {
        ModelAndView tempView = new ModelAndView("addTask");
        String nameError = (errors.getFieldError("title") != null) ? Objects.requireNonNull(errors.getFieldError("title")).getDefaultMessage() : "";
        String descriptionError = (errors.getFieldError("description") != null) ? Objects.requireNonNull(errors.getFieldError("description")).getDefaultMessage() : "";
        tempView.addObject("nameError", nameError);
        tempView.addObject("descriptionError", descriptionError);
        return tempView;
    }

    private void saveTask(Task task) throws IOException {
        SaveTask save = new SaveTask(Constants.SavePath());
        save.save(task);
        taskDAO.addTask(task);
    }
}