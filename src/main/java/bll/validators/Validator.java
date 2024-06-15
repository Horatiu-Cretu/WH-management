package bll.validators;

public interface Validator<T> {
    /**
     * this is an interface to validate the data inputed in the interface
     * */
    boolean validate(T t);
}
