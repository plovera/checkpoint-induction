package util;

import data.ErrorResponse;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Date;
import java.util.List;

public class ValidationUtil {

    public static String VALIDATE_ERROR = "Validate error";
    public static String INPUT_DATA_FAILED = "Input data failed";
    public static String PREFERENCE_ERROR = "Preference Error";
    public static String NOT_VALID = " is not valid." ;
    public static String INIT_POINT_NULL = "Init point is null.";




    public static boolean hasValue(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean hasValue(Integer value) {
        return value != null && value > 0;
    }

    public static boolean hasValue(Float value){
        return value != null && value > 0;
    }

    public static boolean isEmail(String value) {
        return EmailValidator.getInstance().isValid(value);
    }

    public static <E> boolean hasValue(List<E> value) { return value != null && !value.isEmpty(); }

    public static boolean hasValue(Date value) { return value != null && value.getTime() > 0; }


    public static String validateHasValue(String value, String name) {
        if(hasValue(value)) {
            return null;
        }
        return name + NOT_VALID;
    }

    public static String validateHasValue(Integer value, String name) {
        if(hasValue(value)) {
            return null;
        }
        return name + NOT_VALID;
    }

    public static String validateHasValue(Float value, String name) {
        if(hasValue(value)) {
            return null;
        }
        return name + NOT_VALID;
    }

    public static String validateIsEmail(String value, String name) {
        if (isEmail(value)) {
            return null;
        }
        return name + NOT_VALID;
    }
}
