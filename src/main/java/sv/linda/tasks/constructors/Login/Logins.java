package sv.linda.tasks.constructors.Login;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
