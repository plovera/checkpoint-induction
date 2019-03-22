package util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidationUtilTest {

    @Test
    public void hasValueString() {
        // true
        Assertions.assertTrue(ValidationUtil.hasValue("a"));
        Assertions.assertTrue(ValidationUtil.hasValue("1"));
        Assertions.assertTrue(ValidationUtil.hasValue("test"));
        // false
        Assertions.assertFalse(ValidationUtil.hasValue(""));
        String valueNull = null;
        Assertions.assertFalse(ValidationUtil.hasValue(valueNull));
    }

    @Test
    public void hasValueInteger() {
        // true
        Assertions.assertTrue(ValidationUtil.hasValue(1));
        Assertions.assertTrue(ValidationUtil.hasValue(12345));
        // false
        Assertions.assertFalse(ValidationUtil.hasValue(0));
        Assertions.assertFalse(ValidationUtil.hasValue(-1));
        Integer valueNull = null;
        Assertions.assertFalse(ValidationUtil.hasValue(valueNull));
    }

    @Test
    public void hasValueFloat() {
        // true
        Assertions.assertTrue(ValidationUtil.hasValue(1.1F));
        Assertions.assertTrue(ValidationUtil.hasValue(0.1F));
        Assertions.assertTrue(ValidationUtil.hasValue(12345.1234F));
        // false
        Assertions.assertFalse(ValidationUtil.hasValue(0.0F));
        Assertions.assertFalse(ValidationUtil.hasValue(-1.5F));
        Float valueNull = null;
        Assertions.assertFalse(ValidationUtil.hasValue(valueNull));
    }

    @Test
    public void isMail() {
        //true
        Assertions.assertTrue(ValidationUtil.isEmail("test@test.com"));
        Assertions.assertTrue(ValidationUtil.isEmail("a@a.com.ar"));
        Assertions.assertTrue(ValidationUtil.isEmail("a.test@test.com"));
        //false
        Assertions.assertFalse(ValidationUtil.isEmail("test@test"));
        Assertions.assertFalse(ValidationUtil.isEmail("@test.com"));
        Assertions.assertFalse(ValidationUtil.isEmail("test@.com"));
        Assertions.assertFalse(ValidationUtil.isEmail("testtest.com"));
        Assertions.assertFalse(ValidationUtil.isEmail("te@st@test.com"));
    }
}
