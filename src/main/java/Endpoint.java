import com.google.common.net.MediaType;
import com.mercadopago.exceptions.MPException;
import controller.Home;
import controller.PreferenceController;
import controller.TokenizeController;
import data.ErrorResponse;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.servlet.SparkApplication;
import util.JsonUtil;
import util.ResponseUtil;
import util.Path;

import java.io.IOException;

import static spark.Spark.*;

public class Endpoint implements SparkApplication {

    @Override
    public void init() {

        JsonUtil json = new JsonUtil();

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
        post("*", (request, response) -> {
            response.status(HttpStatus.NOT_FOUND_404);
            return HttpStatus.getMessage(HttpStatus.NOT_FOUND_404);
        });


        after(Endpoint::afterHandler);

        // exception
        exception(IOException.class, Endpoint::ioExceptionHandler);
        exception(MPException.class, Endpoint::mpExceptionHandler);
        exception(Exception.class, Endpoint::exceptionHandler);

    }

    /**
     *  After handler
     *  If it does not have header, add json content type
     * @param request
     * @param response
     * @throws Exception
     */
    private static void afterHandler(Request request, Response response) throws Exception {
        ResponseUtil.buildJson(response);

    }

    /**
     * IO exception
     * @param exception
     * @param request
     * @param response
     */
    private static void ioExceptionHandler(IOException exception, Request request, spark.Response response) {
        String message = exception.getMessage().isEmpty() ? exception.toString() : exception.getMessage();
        ResponseUtil.buildException(response, HttpStatus.BAD_REQUEST_400, message);
    }


    /**
     * MP exception
     * @param exception
     * @param request
     * @param response
     */
    private static void mpExceptionHandler(MPException exception, Request request, Response response) {
        String message = exception.getMessage().isEmpty() ? exception.toString() : exception.getMessage();
        ResponseUtil.buildException(response, exception.getStatusCode(), message);
    }

    /**
     * Default exception
     * @param exception
     * @param request
     * @param response
     */
    private static void exceptionHandler(Exception exception, Request request, spark.Response response) {
        String message = exception.getMessage().isEmpty() ? exception.toString() : exception.getMessage();
        ResponseUtil.buildException(response, HttpStatus.INTERNAL_SERVER_ERROR_500, message);
    }
}
