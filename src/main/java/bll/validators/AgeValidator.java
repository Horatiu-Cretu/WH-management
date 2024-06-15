package bll.validators;

import model.Client;

/**
 * @Author: Cretu Horatiu
 * @Since: may 2024
 */
public class AgeValidator implements Validator<Client> {
    private static final int MIN_AGE = 7;
    private static final int MAX_AGE = 80;

    public boolean validate(Client t) {

        if (t.getAge() < MIN_AGE || t.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("The Client Age limit is not respected!");
        }
        return true;
    }

}

