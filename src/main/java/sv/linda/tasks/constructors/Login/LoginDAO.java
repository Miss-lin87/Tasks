package sv.linda.tasks.constructors.Login;

import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.linda.tasks.Constants;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Converter;

@Service
public class LoginDAO implements Constants {
    private final Logins logins;
    private final Converter convert;
    private final DataBaseFunctions database;

    @Autowired
    public LoginDAO(Converter convert, Logins logins, DataBaseFunctions database) {
        this.convert = convert;
        this.logins = logins;
        this.database = database;
    }

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