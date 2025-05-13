package sv.linda.tasks.constructors.Login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LoginsTests {
    @Test
    void testLogins() {
        Logins logins = new Logins();
        Assertions.assertAll(
                () -> Assertions.assertTrue(logins.getLoginList().isEmpty()),
                () -> Assertions.assertNotNull(logins.getLoginList())
        );
    }
}
