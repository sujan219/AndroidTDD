package tdd.test.com.tdd;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import tdd.test.com.validator.EmailValidator;
import tdd.test.com.validator.Validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by sujan on 1/9/2017.
 */

public class EmailValidatorTest {
    private Validator<String> validator;

    @Mock
    private Context context;

    @Before
    public void setup() throws Exception {
        validator = new EmailValidator();
        MockitoAnnotations.initMocks(this);
        Mockito.when(context.getPackageName()).thenReturn("my package");
    }

    @Test
    public void testValidator(){
        assertFalse(validator.validate(null));
        assertFalse(validator.validate("test.sdf"));
        assertTrue(validator.validate("sujan@shrestha.com"));
        System.out.println(context.getPackageName());
    }
}