package tdd.test.com.validator;

/**
 * Created by sujan on 1/9/2017.
 */

public class EmailValidator implements Validator<String> {
    @Override
    public boolean validate(String data) {
        if(data == null){
            return false;
        }
        int i = data.indexOf("@");
        int j = data.indexOf(".");
        return i != -1 && j != -1 && j>i;
    }
}