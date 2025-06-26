package sv.linda.tasks.controllers;

import com.google.gson.Gson;
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
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.enums.Status;
import sv.linda.tasks.functions.Save;
import sv.linda.tasks.validation.CreateLoginValidator;
import sv.linda.tasks.validation.LoginValidator;
import sv.linda.tasks.validation.TaskValidator;
import java.net.URI;
import java.util.Objects;
import java.util.Properties;

@RestController
public class TaskController implements WebMvcConfigurer, Constants {
    private final TaskDAO taskDAO;
    private final LoginDAO loginDAO;
    private final TaskValidator taskValidator;
    private final LoginValidator loginValidator;
    private final CreateLoginValidator createLoginValidator;
    private final ViewPages viewPages;
    private final Properties properties;
    private final Save save;
    private final DataBaseFunctions database;
    private final Gson gson;

    @Autowired
    public TaskController(TaskDAO taskDAO,
                          LoginDAO loginDAO, TaskValidator valid,
                          LoginValidator loginValidator, CreateLoginValidator createLoginValidator,
                          ViewPages viewPages, Properties properties,
                          Save save, DataBaseFunctions database,
                          Gson gson) {
        this.taskDAO = taskDAO;
        this.loginDAO = loginDAO;
        this.taskValidator = valid;
        this.loginValidator = loginValidator;
        this.createLoginValidator = createLoginValidator;
        this.viewPages = viewPages;
        this.properties = properties;
        this.save = save;
        this.database = database;
        this.gson = gson;
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
        return viewPages.getPage(ADD_TASK);
    }

    @PostMapping("/addTask")
    public ModelAndView newTask(Task task, BindingResult bind) {
        Errors errors = new BeanPropertyBindingResult(task, "task");
        taskValidator.validate(task, errors);
        if (errors.hasErrors()) {
            return setTaskErrors(errors);
        } else {
            saveTask(task);
        }
        return new ModelAndView(MAIN);
    }

    @GetMapping("/addLogin")
    public ModelAndView newLogin(@Valid Login login, Errors errors) {
        return viewPages.getPage(ADD_LOGIN);
    }

    @PostMapping("/addLogin")
    public ModelAndView addLogin(Login login, BindingResult bind) {
        Errors errors = new BeanPropertyBindingResult(login, "login");
        createLoginValidator.validate(login, errors);
        if (errors.hasErrors()) {
            return setCreateLoginErrors(errors);
        } else {
            saveLogin(login);
        }
        return viewPages.getPage(LOGIN);
    }

    @PostMapping("/updateTask")
    public ModelAndView updateTask(HttpServletRequest request) {
        for (Task task : taskDAO.getTasks().getTaskList()) {
            task.changeStatus(Status.toEnum(request.getParameter("Selected" + task.getTitle())));
            save.save(task);
        }
        return new ModelAndView(MAIN);
    }

    @PostMapping("/deleteTask/{name}")
    public ModelAndView deleteTask(@PathVariable String name) {
        taskDAO.getTasks().getTaskList().removeIf(task -> task.getTitle().equals(name));
        database.deleteOne(name,TASKS);
        return new ModelAndView(MAIN);
    }

    @GetMapping("/main")
    public ModelAndView mainPage() {
        ModelAndView tempView = viewPages.getPage(TASKS_PAGE);
        tempView.addObject("tasks", taskDAO.getTasks().getTaskList());
        tempView.addObject("Statuses", Status.getAll());
        return tempView;
    }

    @GetMapping("/")
    public ModelAndView loadLoginPage(@Valid Login login, Errors errors) {
        return viewPages.getPage(LOGIN);
    }

    @PostMapping("/login")
    public ModelAndView login(Login login, BindingResult bind) {
        Errors errors = new BeanPropertyBindingResult(login, "login");
        loginValidator.validate(login, errors);
        if (errors.hasErrors()) {
            return setLoginErrors(errors);
        }
        return new ModelAndView(MAIN);
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
        ModelAndView tempView = viewPages.getPage(ADD_TASK);
        String nameError = (errors.getFieldError("title") != null) ? Objects.requireNonNull(errors.getFieldError("title")).getDefaultMessage() : "";
        String descriptionError = (errors.getFieldError("description") != null) ? Objects.requireNonNull(errors.getFieldError("description")).getDefaultMessage() : "";
        tempView.addObject("nameError", nameError);
        tempView.addObject("descriptionError", descriptionError);
        return tempView;
    }

    private ModelAndView setLoginErrors(Errors errors) {
        ModelAndView tempView = viewPages.getPage(LOGIN);
        String usernameError = (errors.getFieldError("username") != null) ? Objects.requireNonNull(errors.getFieldError("username")).getDefaultMessage() : "";
        String passwordError = (errors.getFieldError("password") != null) ? Objects.requireNonNull(errors.getFieldError("password")).getDefaultMessage() : "";
        tempView.addObject("usernameError", usernameError);
        tempView.addObject("passwordError", passwordError);
        return tempView;
    }

    private ModelAndView setCreateLoginErrors(Errors errors) {
        ModelAndView tempView = viewPages.getPage(ADD_LOGIN);
        String usernameError = (errors.getFieldError("username") != null) ? Objects.requireNonNull(errors.getFieldError("username")).getDefaultMessage() : "";
        String passwordError = (errors.getFieldError("password") != null) ? Objects.requireNonNull(errors.getFieldError("password")).getDefaultMessage() : "";
        tempView.addObject("usernameError", usernameError);
        tempView.addObject("passwordError", passwordError);
        return tempView;
    }

    private void saveTask(Task task) {
        save.save(task);
        taskDAO.addTask(task);
    }

    private void saveLogin(Login login) {
        save.save(login);
        loginDAO.addLogin(login);
    }
}