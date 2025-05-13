package sv.linda.tasks.constructors.Login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LoginTests {

    @Test
    void makeEmptyLoginTest() {
        Login tempLogin = new Login();
        Assertions.assertAll(
                () -> Assertions.assertEquals("", tempLogin.getUsername()),
                () -> Assertions.assertEquals("", tempLogin.getPassword()),
                () -> Assertions.assertNotNull(tempLogin)
        );
    }

    @Test
    void makeFullLoginTest() {
        Login tempLogin = new Login("admin", "admin");
        Assertions.assertAll(
                () -> Assertions.assertEquals("admin", tempLogin.getUsername()),
                () -> Assertions.assertEquals("admin", tempLogin.getPassword()),
                () -> Assertions.assertNotNull(tempLogin)
        );
    }
}
