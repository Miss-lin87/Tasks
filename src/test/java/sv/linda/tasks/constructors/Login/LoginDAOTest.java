package sv.linda.tasks.constructors.Login;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class LoginDAOTest {

    @Test
    void testEmptyLoginDAO() {
        LoginDAO tempDAO = Mockito.mock(LoginDAO.class);
        assertAll(
                () -> assertTrue(tempDAO.getLogins().getLoginList().isEmpty()),
                () -> assertNotEquals(null, tempDAO.getLogins().getLoginList()),
                () -> assertFalse(tempDAO.getLogins().getLoginList().contains(new Login("admin", "admin")))
        );
    }

    @Test
    void testFillLoginDAO() {
        LoginDAO tempDAO = Mockito.mock(LoginDAO.class);
        for (int i = 0; i < 10; i++) {
            Login templogin = new Login("User" + i, "password" + i);
            tempDAO.addLogin(templogin);
        }
        assertAll(
                () -> assertEquals(10, tempDAO.getLogins().getLoginList().size()),
                () -> assertTrue(tempDAO.getLogins().getLoginList().contains(new Login("User0", "password0"))),
                () -> assertTrue(tempDAO.getLogins().getLoginList().contains(new Login("User9", "password9"))),
                () -> assertFalse(tempDAO.getLogins().getLoginList().contains(new Login("User100", "password100")))
        );
    }
}
