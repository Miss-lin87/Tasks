package sv.linda.tasks.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sv.linda.tasks.constructors.Login.Login;

import java.util.List;

public class LoginValidator implements Validator {
    private final List<Login> loginList;

    public LoginValidator(List<Login> loginList) {
        this.loginList = loginList;
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
            errors.rejectValue("username", "username.empty", "You need to enter a username");
        } else if (loginList.stream().noneMatch(login -> login.getUsername().equals(userLogin.getUsername()))) {
            errors.rejectValue("username", "username.is.wrong", "You entered a wrong username");
        }
    }

    private void validatePassword(Login userLogin, Errors errors) {
        if (userLogin.getPassword() == null || userLogin.getPassword().trim().isEmpty()) {
            errors.rejectValue("password", "password.empty", "You need to enter a password");
        } else if (loginList.stream().anyMatch(login -> login.getUsername().equals(userLogin.getUsername()) &&
                !login.getPassword().equals(userLogin.getPassword()))) {
            errors.rejectValue("password", "password.is.wrong", "You entered a wrong password");
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
