import com.google.common.net.MediaType;
import data.ErrorResponse;
import org.eclipse.jetty.http.HttpStatus;
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

        get("/", Home::home);
        get(Path.Web.HOME, Home::home);
        post(Path.Web.API_PREFERENCE, Preference::preference);
        get(Path.Web.CREATE_PREFERENCE, Preference::create);
        get(Path.Web.PREFERENCE_V1, Preference::preferenceV1);
        get(Path.Web.PREFERENCE_V2, Preference::preferenceV2);
        post(Path.Web.PREFERENCE_PROCESS, Preference::process);

        get(Path.Web.TOKENIZE_V1, Tokenize::tokenize);
        get(Path.Web.TOKENIZE_V2, Tokenize::tokenizev2);
        post(Path.Web.TOKENIZE_PAYMENT, Tokenize::payment);


        get("*", (request, response) -> {
            response.status(HttpStatus.NOT_FOUND_404);
            return HttpStatus.getMessage(HttpStatus.NOT_FOUND_404);
        });

        after(Main::afterHandler);

        exception(Exception.class, Main::exceptionHandler);


        MercadoPago.SDK.setClientSecret(Credential.Basic.CLIENT_SECRET_OK);
        MercadoPago.SDK.setClientId(Credential.Basic.CLIENT_ID_OK);

        MercadoPago.SDK.setAccessToken(Credential.Customer.ACCESS_TOKEN);
    }

    private static void afterHandler(Request request, Response response) throws Exception {
        // if not set, set content-type as 'application/json;charset=utf-8'
        if (!response.raw().containsHeader("Content-Type")) {
            response.header("Content-Type", MediaType.JSON_UTF_8.toString());
        }
    }

    private static void exceptionHandler(Exception exception, Request request, Response response) {
        String message = exception.getMessage().isEmpty() ? exception.toString() : exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR_500,
                HttpStatus.getMessage(HttpStatus.INTERNAL_SERVER_ERROR_500), message);
        buildResponse(response, errorResponse);
    }

    private static void buildResponse(Response response, ErrorResponse errorResponse) {
        response.header("Content-Type", MediaType.JSON_UTF_8.toString());
        String responseBody = Json.errorDataToJson(errorResponse);
        response.body(responseBody);
        response.status(errorResponse.getHttpStatusCode());
    }
}