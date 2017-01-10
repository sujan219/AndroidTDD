package tdd.test.com.validator;

/**
 * Created by sujan on 1/9/2017.
 */

public interface Validator<T> {
    public boolean validate(T input);
}
