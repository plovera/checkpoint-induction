import com.google.common.net.MediaType;
import controller.Home;
import controller.PreferenceController;
import controller.TokenizeController;
import data.ErrorResponse;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.servlet.SparkApplication;
import util.ResponseUtil;
import util.Json;
import util.Path;

import java.io.IOException;

import static spark.Spark.*;

public class Endpoint implements SparkApplication {

    @Override
    public void init() {

        Json json = new Json();

        get("/", Home::home);
        get(Path.Web.HOME, Home::home);
        post(Path.Web.CREATE_PREFERENCE, MediaType.JSON_UTF_8.toString(), PreferenceController::create, json);
        get(Path.Web.REDIRECT_PREFERENCE, PreferenceController::redirect);
        get(Path.Web.PREFERENCE_V1, PreferenceController::preferenceV1);
        get(Path.Web.PREFERENCE_V2, PreferenceController::preferenceV2);
        post(Path.Web.PREFERENCE_PROCESS, PreferenceController::process);

        get(Path.Web.TOKENIZE_V1, TokenizeController::tokenize);
        get(Path.Web.TOKENIZE_V2, TokenizeController::tokenizev2);
        post(Path.Web.TOKENIZE_PAYMENT, MediaType.JSON_UTF_8.toString(), TokenizeController::payment, json);

        // default endpoint
        get("*", (request, response) -> {
            response.status(HttpStatus.NOT_FOUND_404);
            return HttpStatus.getMessage(HttpStatus.NOT_FOUND_404);
        });


        after(Endpoint::afterHandler);

        // Default exception
        exception(IOException.class, Endpoint::ioExceptionHandler);
        exception(Exception.class, Endpoint::exceptionHandler);

    }

    /**
     *  After handler
     * @param request
     * @param response
     * @throws Exception
     */
    private static void afterHandler(Request request, spark.Response response) throws Exception {
        ResponseUtil.buildJson(response);

    }

    /**
     * IO exception
     * @param exception
     * @param request
     * @param response
     */
    private static void ioExceptionHandler(Exception exception, Request request, spark.Response response) {
        String message = exception.getMessage().isEmpty() ? exception.toString() : exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.getMessage(HttpStatus.BAD_REQUEST_400), message);
        response.status(HttpStatus.BAD_REQUEST_400);
        ResponseUtil.buildException(response, errorResponse);
    }

    /**
     * Default exception
     * @param exception
     * @param request
     * @param response
     */
    private static void exceptionHandler(Exception exception, Request request, spark.Response response) {
        response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        String message = exception.getMessage().isEmpty() ? exception.toString() : exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.getMessage(HttpStatus.INTERNAL_SERVER_ERROR_500), message);
        ResponseUtil.buildException(response, errorResponse);
    }
}
