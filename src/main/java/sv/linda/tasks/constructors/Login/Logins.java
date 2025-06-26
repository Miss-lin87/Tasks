package sv.linda.tasks.constructors.Login;

import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Setter
public class Logins {
    private List<Login> loginList;

    public List<Login> getLoginList() {
        if (loginList == null) {
            loginList = new ArrayList<>();
        }
        return loginList;
    }
}
