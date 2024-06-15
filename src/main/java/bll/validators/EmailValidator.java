package bll.validators;

import model.Client;
import java.util.regex.Pattern;

/**
 * @Author: Cretu Horatiu
 * @Since: May 2024
 */
public class EmailValidator implements Validator<Client> {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public boolean validate(Client t) {
        if (t == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        String email = t.getEmail();
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Email is not a valid email!");
        }
        return true;
    }
}
