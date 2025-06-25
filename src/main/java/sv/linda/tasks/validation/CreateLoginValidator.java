package sv.linda.tasks.validation;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Login.Login;

import java.util.List;
import java.util.regex.Pattern;

public class CreateLoginValidator implements Validator, Constants {
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
            errors.rejectValue(USERNAME, "username.empty", "You need to enter a username");
        } else if (logins.stream().anyMatch(existingLogin -> existingLogin.getUsername().equals(login.getUsername()))) {
            errors.rejectValue(USERNAME, "username.already.exists", "That username is already taken");
        }
    }

    private void validatePassword (Login login, Errors errors) {
        if (login.getPassword() == null || login.getPassword().trim().isEmpty()) {
            errors.rejectValue(PASSWORD, "password.empty", "You need to enter a password");
        } else if (login.getPassword().length() < 6) {
            errors.rejectValue(PASSWORD, "password.too.short", "Password must be at least 6 characters long");
        } else if (!Pattern.compile("[a-zA-Z]").matcher(login.getPassword()).find() || !Pattern.compile("[0-9]").matcher(login.getPassword()).find()) {
            errors.rejectValue(PASSWORD, "password.weak", "Password must contain letter and numbers");
        }
    }

    private void validateFullLogin(Login login, Errors errors) {
        validateUsername(login, errors);
        validatePassword(login, errors);
    }
}