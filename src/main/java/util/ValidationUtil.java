package util;

import data.ErrorResponse;
import org.apache.commons.validator.routines.EmailValidator;

public class ValidationUtil {

    public static String VALIDATE_ERROR = "Validate error";
    public static String INPUT_DATA_FAILED = "Input data failed";
    public static String PREFERENCE_ERROR = "Preference Error";




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


    public static ErrorResponse validateHasValue(String value, String name) {
        if(hasValue(value)) {
            return null;
        }
        return new ErrorResponse(VALIDATE_ERROR,  name + " is not valid.");
    }

    public static ErrorResponse validateHasValue(Integer value, String name) {
        if(hasValue(value)) {
            return null;
        }
        return new ErrorResponse(VALIDATE_ERROR, name + " is not valid.");
    }

    public static ErrorResponse validateHasValue(Float value, String name) {
        if(hasValue(value)) {
            return null;
        }
        return new ErrorResponse(VALIDATE_ERROR, name + " is not valid.");
    }

    public static ErrorResponse validateIsEmail(String value, String name) {
        if (isEmail(value)) {
            return null;
        }
        return new ErrorResponse(VALIDATE_ERROR, name + " is not valid");
    }
}
