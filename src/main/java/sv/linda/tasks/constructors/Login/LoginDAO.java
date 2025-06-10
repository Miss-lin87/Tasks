package sv.linda.tasks.constructors.Login;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.util.List;

@Repository
public class LoginDAO {
    private final Logins logins = new Logins();
    private List<Login> loginList;

    @PostConstruct
    public void init() {
        loginList = List.of(new Login("admin", "admin"), new Login("user", "user"));
    }

    public Logins getLogins() {
        return logins;
    }

    public void addTask(Login login) {
        logins.getLoginList().add(login);
    }
}