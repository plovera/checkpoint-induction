import com.google.common.net.MediaType;
import data.ErrorResponse;
import org.eclipse.jetty.http.HttpStatus;
import util.BuildResponse;
import util.Credential;
import util.Json;
import util.Path;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;

import static spark.Spark.*;
import spark.Response;
import spark.Request;


public class Main {


    public static void main(String[] arg) throws MPException {


        port(4567);

        // endpoint
        get("/", Home::home);
        get(Path.Web.HOME, Home::home);
        post(Path.Web.API_PREFERENCE, PreferenceController::preference);
        get(Path.Web.CREATE_PREFERENCE, PreferenceController::create);
        get(Path.Web.PREFERENCE_V1, PreferenceController::preferenceV1);
        get(Path.Web.PREFERENCE_V2, PreferenceController::preferenceV2);
        post(Path.Web.PREFERENCE_PROCESS, PreferenceController::process);

        get(Path.Web.TOKENIZE_V1, TokenizeController::tokenize);
        get(Path.Web.TOKENIZE_V2, TokenizeController::tokenizev2);
        post(Path.Web.TOKENIZE_PAYMENT, MediaType.JSON_UTF_8.toString(), TokenizeController::payment, new Json());

        // default endpoint
        get("*", (request, response) -> {
            response.status(HttpStatus.NOT_FOUND_404);
            return HttpStatus.getMessage(HttpStatus.NOT_FOUND_404);
        });


        after(Main::afterHandler);

        // Default exception
        exception(Exception.class, Main::exceptionHandler);


        // credentials
        MercadoPago.SDK.setClientSecret(Credential.Basic.CLIENT_SECRET_OK);
        MercadoPago.SDK.setClientId(Credential.Basic.CLIENT_ID_OK);
        MercadoPago.SDK.setAccessToken(Credential.Customer.ACCESS_TOKEN);
    }

    /**
     *  After handler
     * @param request
     * @param response
     * @throws Exception
     */
    private static void afterHandler(Request request, Response response) throws Exception {
        // if not set, set content-type as 'application/json;charset=utf-8'
        if (!response.raw().containsHeader("Content-Type")) {
            response.header("Content-Type", MediaType.JSON_UTF_8.toString());
        }
    }

    /**
     * Default exception
     * @param exception
     * @param request
     * @param response
     */
    private static void exceptionHandler(Exception exception, Request request, Response response) {
        String message = exception.getMessage().isEmpty() ? exception.toString() : exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR_500,
                HttpStatus.getMessage(HttpStatus.INTERNAL_SERVER_ERROR_500), message);
        BuildResponse.ExceptionBuild(response, errorResponse);
    }
}