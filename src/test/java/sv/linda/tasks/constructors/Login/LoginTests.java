package sv.linda.tasks.constructors.Login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTests {

    @Test
    void makeEmptyLoginTest() {
        Login tempLogin = new Login();
        assertAll(
                () -> assertEquals("", tempLogin.getUsername()),
                () -> assertEquals("", tempLogin.getPassword()),
                () -> assertNotNull(tempLogin)
        );
    }

    @Test
    void makeFullLoginTest() {
        Login tempLogin = new Login("admin", "admin");
        assertAll(
                () -> assertEquals("admin", tempLogin.getUsername()),
                () -> assertEquals("admin", tempLogin.getPassword()),
                () -> assertNotNull(tempLogin)
        );
    }
}
