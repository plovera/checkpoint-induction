package controller;

import com.mercadopago.exceptions.MPException;
import data.InductionException;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import util.ResponseUtil;
import util.ValidationUtil;

import java.io.IOException;

public class ExceptionController {

    /**
     * IO exception
     * @param exception
     * @param request
     * @param response
     */
    public static void ioExceptionHandler(IOException exception, Request request, Response response) {
        String message = ValidationUtil.hasValue(exception.getMessage()) ? exception.getMessage() : exception.toString();
        ResponseUtil.buildException(response, HttpStatus.BAD_REQUEST_400, message);
    }

    /**
     * Induction Exception of this aplication
     * @param request
     * @param response
     */
    public static void inductionExceptionHandler(InductionException exception, Request request, Response response) {
        String message = ValidationUtil.hasValue(exception.getMessage())?  exception.getMessage() : exception.toString();
        ResponseUtil.buildException(response, exception.getStatusCode(), message, exception.getErrors());

    }

    /**
     * exception of Mercado Pago
     * @param exception
     * @param request
     * @param response
     */
    public static void mpExceptionHandler(MPException exception, Request request, Response response) {
        String message = exception.getMessage().isEmpty() ?  exception.getMessage() : exception.toString();
        ResponseUtil.buildException(response, exception.getStatusCode(), message);
    }

    /**
     * Default exception
     * @param exception
     * @param request
     * @param response
     */
    public static void exceptionHandler(Exception exception, Request request, Response response) {
        String message = ValidationUtil.hasValue(exception.getMessage())? exception.getMessage() : exception.toString();
        ResponseUtil.buildException(response, HttpStatus.INTERNAL_SERVER_ERROR_500, message);
    }
}
