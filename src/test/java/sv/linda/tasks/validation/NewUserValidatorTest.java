package sv.linda.tasks.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import sv.linda.tasks.constructors.Login.Login;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class NewUserValidatorTest {
    private CreateLoginValidator valid;
    private Login login;
    private Errors errors;
    private final List<Login> mockLoginList = List.of(
            new Login("User1", "Password1"),
            new Login("User2", "Password2"),
            new Login("User3", "Password3")
    );

    @BeforeEach
    void setUp() {
        valid = new CreateLoginValidator(mockLoginList);
        login = new Login();
        errors = new BeanPropertyBindingResult(login, "login");
    }

    @Test
    void emptyNameTest() {
        login.setUsername("");
        login.setPassword("Password4");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("username")),
                () -> assertEquals("You need to enter a username", Objects.requireNonNull(errors.getFieldError("username")).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors("password"))
        );
    }

    @Test
    void emptyPasswordTest() {
        login.setUsername("User4");
        login.setPassword("");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("password")),
                () -> assertEquals("You need to enter a password", Objects.requireNonNull(errors.getFieldError("password")).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors("username"))
        );
    }

    @Test
    void invalidPasswordTest() {
        login.setUsername("User4");
        login.setPassword("Password");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("password")),
                () -> assertEquals("Password must contain letter and numbers", Objects.requireNonNull(errors.getFieldError("password")).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors("username"))
        );
    }

    @Test
    void shortPasswordTest() {
        login.setUsername("User4");
        login.setPassword("Pa12");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("password")),
                () -> assertEquals("Password must be at least 6 characters", Objects.requireNonNull(errors.getFieldError("password")).getDefaultMessage()),
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
    void userAlreadyExistsTest() {
        login.setUsername("User1");
        login.setPassword("Password2");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("username")),
                () -> assertEquals("That username is already taken", Objects.requireNonNull(errors.getFieldError("username")).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors("password"))
        );
    }
}