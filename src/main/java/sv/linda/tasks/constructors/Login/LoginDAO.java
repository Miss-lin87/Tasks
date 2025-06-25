package sv.linda.tasks.constructors.Login;

import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.stereotype.Repository;
import sv.linda.tasks.Constants;
import sv.linda.tasks.functions.Converter;

@Repository
public class LoginDAO implements Constants {
    private final Logins logins = new Logins();
    private final Converter convert = new Converter(gson);

    @PostConstruct
    public void init() {
        for (Document doc : database.getAllData(USERS).find()) {
            Login login = convert.toLogin(doc);
            logins.getLoginList().add(login);
        }
    }

    public Logins getLogins() {
        return logins;
    }

    public void addLogin(Login login) {
        logins.getLoginList().add(login);
    }
}