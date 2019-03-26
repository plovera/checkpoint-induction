package data;


import util.ValidationUtil;

import java.util.List;

/**
 * Induction Exception
 */
public class InductionException extends Exception {

    private Integer statusCode;
    private List<String> errors;

    public InductionException(String message) {
        super(message);
    }

    public InductionException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public InductionException(String message, Integer statusCode, List<String> errors) {
        super(message);
        this.statusCode = statusCode;
        this.errors = errors;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append(super.toString());
        if(ValidationUtil.hasValue(statusCode)) {
            message.append("; status_code: ");
            message.append(statusCode);
        }
        if(ValidationUtil.hasValue(errors)) {
            message.append("; errors: ");
            message.append(String.join(", ", errors));
        }
        return message.toString();

    }
}
