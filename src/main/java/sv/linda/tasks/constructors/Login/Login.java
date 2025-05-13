package sv.linda.tasks.constructors.Login;

import lombok.Data;

@Data
public class Login {
    private String username;
    private String password;

    public Login() {
        this.username = "";
        this.password = "";
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
