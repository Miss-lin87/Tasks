package sv.linda.tasks.constructors.Login;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Converter;

import static org.junit.jupiter.api.Assertions.*;

class LoginDAOTest {
    @Mock
    DataBaseFunctions database;
    @Mock
    Converter convert;
    LoginDAO loginDAO;

    @BeforeEach
    void init() {
        loginDAO = new LoginDAO(convert,new Logins(),database);
    }

    @Test
    void testEmptyLoginDAO() {
        assertAll(
                () -> assertTrue(loginDAO.getLogins().getLoginList().isEmpty()),
                () -> assertNotEquals(null, loginDAO.getLogins().getLoginList()),
                () -> assertFalse(loginDAO.getLogins().getLoginList().contains(new Login("admin", "admin")))
        );
    }

    @Test
    void testFillLoginDAO() {
        for (int i = 0; i < 10; i++) {
            Login templogin = new Login("User" + i, "password" + i);
            loginDAO.addLogin(templogin);
        }
        assertAll(
                () -> assertEquals(10, loginDAO.getLogins().getLoginList().size()),
                () -> assertTrue(loginDAO.getLogins().getLoginList().contains(new Login("User0", "password0"))),
                () -> assertTrue(loginDAO.getLogins().getLoginList().contains(new Login("User9", "password9"))),
                () -> assertFalse(loginDAO.getLogins().getLoginList().contains(new Login("User100", "password100")))
        );
    }
}