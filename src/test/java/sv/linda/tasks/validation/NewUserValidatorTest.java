package sv.linda.tasks.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Login.LoginDAO;
import sv.linda.tasks.constructors.Login.Logins;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Converter;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class NewUserValidatorTest implements Constants {
    @Mock
    DataBaseFunctions databse;
    @Mock
    Converter converter;
    private Logins logins = new Logins();
    private CreateLoginValidator valid;
    private Login login;
    private Errors errors;
    private LoginDAO loginDAO = new LoginDAO(converter, logins, databse);
    private final List<Login> mockLoginList = List.of(
            new Login("User1", "Password1"),
            new Login("User2", "Password2"),
            new Login("User3", "Password3")
    );

    @BeforeEach
    void setUp() {
        loginDAO.getLogins().setLoginList(mockLoginList);
        valid = new CreateLoginValidator(loginDAO);
        login = new Login();
        errors = new BeanPropertyBindingResult(login, "login");
    }

    @Test
    void emptyNameTest() {
        login.setUsername("");
        login.setPassword("Password4");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors(USERNAME)),
                () -> assertEquals("You need to enter a username", Objects.requireNonNull(errors.getFieldError(USERNAME)).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors(PASSWORD))
        );
    }

    @Test
    void emptyPasswordTest() {
        login.setUsername("User4");
        login.setPassword("");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors(PASSWORD)),
                () -> assertEquals("You need to enter a password", Objects.requireNonNull(errors.getFieldError(PASSWORD)).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors(USERNAME))
        );
    }

    @Test
    void invalidPasswordTest() {
        login.setUsername("User4");
        login.setPassword("Password");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors(PASSWORD)),
                () -> assertEquals("Password must contain letter and numbers", Objects.requireNonNull(errors.getFieldError(PASSWORD)).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors(USERNAME))
        );
    }

    @Test
    void shortPasswordTest() {
        login.setUsername("User4");
        login.setPassword("Pa12");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors(PASSWORD)),
                () -> assertEquals("Password must be at least 6 characters long", Objects.requireNonNull(errors.getFieldError(PASSWORD)).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors(USERNAME))
        );
    }

    @Test
    void fullEmptyTest() {
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors(USERNAME)),
                () -> assertTrue(errors.hasFieldErrors(PASSWORD)),
                () -> assertFalse(errors.getAllErrors().isEmpty())
        );
    }

    @Test
    void userAlreadyExistsTest() {
        login.setUsername("User1");
        login.setPassword("Password2");
        valid.validate(login, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors(USERNAME)),
                () -> assertEquals("That username is already taken", Objects.requireNonNull(errors.getFieldError(USERNAME)).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors(PASSWORD))
        );
    }
}