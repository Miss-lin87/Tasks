package sv.linda.tasks.validation;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Login.LoginDAO;
import sv.linda.tasks.constructors.Login.Logins;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Converter;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LoginValidatorTest {
    private LoginValidator valid;
    private Login login;
    private Errors errors;
    private final List<Login> nameList = List.of(
            new Login("User1", "Password1"),
            new Login("User2", "Password2"));

    @BeforeEach
    void setUp() {
        valid = Mockito.mock(LoginValidator.class);
        login = new Login();
        errors = new BeanPropertyBindingResult(login, "login");
    }

    @Test
    void emptyUsernameTest() {
        login.setUsername("");
        login.setPassword("Password1");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("username")),
                () -> assertEquals("You need to enter a username", Objects.requireNonNull(errors.getFieldError("username")).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors("password"))
        );
    }

    @Test
    void emptyPasswordTest() {
        login.setUsername("User1");
        login.setPassword("");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("password")),
                () -> assertEquals("You need to enter a password", Objects.requireNonNull(errors.getFieldError("password")).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors("username"))
        );
    }

    @Test
    void fullEmptyTest() {
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("username")),
                () -> assertTrue(errors.hasFieldErrors("password")),
                () -> assertFalse(errors.getAllErrors().isEmpty())
        );
    }

    @Test
    void wrongPasswordTest() {
        login.setUsername("User1");
        login.setPassword("Password2");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("password")),
                () -> Assertions.assertEquals("You entered a wrong password", Objects.requireNonNull(errors.getFieldError("password")).getDefaultMessage()),
                () -> assertFalse(errors.getAllErrors().isEmpty())
        );
    }
}