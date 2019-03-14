package data;

public class ErrorResponse {

    private final Integer httpStatusCode;
    private final String error;
    private final String message;

    public ErrorResponse(Integer httpStatusCode, String error, String message) {
        this.httpStatusCode = httpStatusCode;
        this.error = error;
        this.message = message;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
