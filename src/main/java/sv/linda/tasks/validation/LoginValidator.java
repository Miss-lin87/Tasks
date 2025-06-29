package sv.linda.tasks.validation;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Login.LoginDAO;

import java.util.ArrayList;
import java.util.List;

public class LoginValidator implements Validator, Constants {
    private final List<Login> loginList;
    private final LoginDAO loginDAO;

    @Autowired
    public LoginValidator(LoginDAO loginDAO) {
        this.loginList = loginDAO.getLogins().getLoginList();
        this.loginDAO = loginDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Login.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Login login = (Login) target;
        if (!validateLogin(login)) {
            validateFullLogin(login, errors);
        }
    }

    private void validateUsername(Login userLogin, Errors errors) {
        if (userLogin.getUsername() == null || userLogin.getUsername().trim().isEmpty()) {
            errors.rejectValue(USERNAME, "username.empty", "You need to enter a username");
        } else if (loginList.stream().noneMatch(login -> login.getUsername().equals(userLogin.getUsername()))) {
            errors.rejectValue(USERNAME, "username.is.wrong", "You entered a wrong username");
        }
    }

    private void validatePassword(Login userLogin, Errors errors) {
        if (userLogin.getPassword() == null || userLogin.getPassword().trim().isEmpty()) {
            errors.rejectValue(PASSWORD, "password.empty", "You need to enter a password");
        } else if (loginList.stream().anyMatch(login -> login.getUsername().equals(userLogin.getUsername()) &&
                !login.getPassword().equals(userLogin.getPassword()))) {
            errors.rejectValue(PASSWORD, "password.is.wrong", "You entered a wrong password");
        }
    }

    private boolean validateLogin(Login login) {
        return loginList.contains(login);
    }

    private void validateFullLogin(Login login, Errors errors) {
        validateUsername(login, errors);
        validatePassword(login, errors);
    }

    @Override
    public Errors validateObject(Object target) {
        return Validator.super.validateObject(target);
    }
}
