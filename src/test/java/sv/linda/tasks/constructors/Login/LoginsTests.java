package sv.linda.tasks.constructors.Login;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginsTests {
    @Test
    void testLogins() {
        Logins logins = new Logins();
        assertAll(
                () -> assertTrue(logins.getLoginList().isEmpty()),
                () -> assertNotNull(logins.getLoginList())
        );
    }
}