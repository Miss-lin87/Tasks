package sv.linda.tasks.constructors.Login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginsTests extends Logins {
    @Test
    void testLogins() {
        assertAll(
                () -> assertTrue(getLoginList().isEmpty()),
                () -> assertNotNull(getLoginList())
        );
    }
}
