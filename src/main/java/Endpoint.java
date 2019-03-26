import com.mercadopago.exceptions.MPException;
import controller.ExceptionController;
import controller.Home;
import controller.PreferenceController;
import controller.TokenizeController;
import data.InductionException;
import org.eclipse.jetty.http.HttpStatus;
import spark.servlet.SparkApplication;
import util.JsonUtil;
import util.ResponseUtil;
import util.Path;

import java.io.IOException;

import static spark.Spark.*;

public class Endpoint implements SparkApplication {

    @Override
    public void init() {


        get("/", Home::home);
        get(Path.Web.HOME, Home::home);
        post(Path.Web.CREATE_PREFERENCE, PreferenceController::create, JsonUtil.INSTANCE);
        get(Path.Web.REDIRECT_PREFERENCE, PreferenceController::redirect);
        get(Path.Web.PREFERENCE_V1, PreferenceController::preferenceV1);
        get(Path.Web.PREFERENCE_V2, PreferenceController::preferenceV2);
        post(Path.Web.PREFERENCE_PROCESS, PreferenceController::process, JsonUtil.INSTANCE);

        get(Path.Web.TOKENIZE_V1, TokenizeController::tokenize);
        get(Path.Web.TOKENIZE_V2, TokenizeController::tokenizev2);
        post(Path.Web.TOKENIZE_PAYMENT, TokenizeController::payment, JsonUtil.INSTANCE);

        // default endpoint
        get("*", (request, response) -> {
            response.status(HttpStatus.NOT_FOUND_404);
            return HttpStatus.getMessage(HttpStatus.NOT_FOUND_404);
        });
        post("*", (request, response) -> {
            response.status(HttpStatus.NOT_FOUND_404);
            return HttpStatus.getMessage(HttpStatus.NOT_FOUND_404);
        });


        after((request, response) ->  ResponseUtil.buildJson(response) );

        // exception
        exception(IOException.class, ExceptionController::ioExceptionHandler);
        exception(InductionException.class, ExceptionController::inductionExceptionHandler);
        exception(MPException.class, ExceptionController::mpExceptionHandler);
        exception(Exception.class, ExceptionController::exceptionHandler);

    }


}
