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
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Login.LoginDAO;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.constructors.Task.TaskDAO;
import sv.linda.tasks.constructors.Task.Tasks;
import sv.linda.tasks.enums.Status;
import sv.linda.tasks.functions.Save;
import sv.linda.tasks.validation.LoginValidator;
import sv.linda.tasks.validation.TaskValidator;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@RestController
public class TaskController implements WebMvcConfigurer {
    private final TaskDAO taskDAO;
    private final LoginDAO loginDAO;
    private final TaskValidator taskValidator;
    private final LoginValidator loginValidator;
    private final String main = "redirect:/";
    private final String SAVE_PATH;

    @Autowired
    public TaskController(TaskDAO taskDAO, LoginDAO loginDAO, TaskValidator valid, LoginValidator loginValidator) {
        this.taskDAO = taskDAO;
        this.loginDAO = loginDAO;
        this.taskValidator = valid;
        this.loginValidator = loginValidator;
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
        taskValidator.validate(task, errors);
        if (errors.hasErrors()) {
            return setTaskErrors(errors);
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
            new Save().save(task);
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

    @GetMapping("/login")
    public ModelAndView loadLoginPage(@Valid Login login, Errors errors) {
        return new ModelAndView("loginPage");
    }

    @PostMapping("/login")
    public ModelAndView login(Login login, BindingResult bind) {
        Errors error = new BeanPropertyBindingResult(login, "logins");
        loginValidator.validate(login, error);
        if (!error.hasErrors()) {
            return new ModelAndView(main);
        } else {
            return setLoginErrors(error);
        }
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

    private ModelAndView setTaskErrors(Errors errors) {
        ModelAndView tempView = new ModelAndView("addTask");
        String nameError = (errors.getFieldError("title") != null) ? Objects.requireNonNull(errors.getFieldError("title")).getDefaultMessage() : "";
        String descriptionError = (errors.getFieldError("description") != null) ? Objects.requireNonNull(errors.getFieldError("description")).getDefaultMessage() : "";
        tempView.addObject("nameError", nameError);
        tempView.addObject("descriptionError", descriptionError);
        return tempView;
    }

    private ModelAndView setLoginErrors(Errors errors) {
        ModelAndView tempView = new ModelAndView("loginPage");
        String usernameError = (errors.getFieldError("username") != null) ? Objects.requireNonNull(errors.getFieldError("username")).getDefaultMessage() : "";
        String passwordError = (errors.getFieldError("password") != null) ? Objects.requireNonNull(errors.getFieldError("password")).getDefaultMessage() : "";
        tempView.addObject("usernameError", usernameError);
        tempView.addObject("passwordError", passwordError);
        return tempView;
    }

    private void saveTask(Task task) throws IOException {
        new Save().save(task);
        taskDAO.addTask(task);
    }
}