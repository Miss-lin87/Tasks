package sv.linda.tasks.validation;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sv.linda.tasks.constructors.Login.Login;

import java.util.List;

public class CreateLoginValidator implements Validator {
    private final List<Login> logins;

    public CreateLoginValidator(List<Login> logins) {
        this.logins = logins;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Login.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Login login = (Login) target;
        validateFullLogin(login, errors);
    }

    @Override
    public Errors validateObject (Object target) {
        return Validator.super.validateObject(target);
    }

    private void validateUsername (Login login, Errors errors) {
        if (login.getUsername() == null || login.getUsername().trim().isEmpty()) {
            errors.rejectValue("username", "username.empty", "You need to enter a username");
        } else if (logins.stream().anyMatch(existingLogin -> existingLogin.getUsername().equals(login.getUsername()))) {
            errors.rejectValue("username", "username.already.exists", "That username is already taken");
        }
    }

    private void validatePassword (Login login, Errors errors) {
        if (login.getPassword() == null || login.getPassword().trim().isEmpty()) {
            errors.rejectValue("password", "password.empty", "You need to enter a password");
        } if (login.getPassword().length() < 6) {
            errors.rejectValue("password", "password.too.short", "Password must be at least 6 characters long");
        }
    }

    private void validateFullLogin(Login login, Errors errors) {
        validateUsername(login, errors);
        validatePassword(login, errors);
    }
}