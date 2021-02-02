package util;

//import com.google.common.net.MediaType;
import com.google.common.net.MediaType;
import data.ErrorResponse;
import org.eclipse.jetty.http.HttpStatus;
import spark.Response;

import java.util.HashMap;

public class ResponseUtil {

    /**
     * Build exception message with JsonUtil format
     * @param response
     * @param httpStatus
     * @param message
     */
    public static void buildException(Response response, Integer httpStatus, String message) {
        buildException(response, httpStatus, message, null);
    }

    /**
     * Build exception message with JsonUtil format
     * @param response
     * @param httpStatus
     * @param message
     */
    public static void buildException(Response response, Integer httpStatus, String message, Object error) {
        String responseBody = buildError(httpStatus, message, error);
        response.body(responseBody);
        response.status(httpStatus);
        response.header("Content-Type", MediaType.JSON_UTF_8.toString());
    }

    /**
     * Build model for error message
     * @param error
     * @return
     */
    public static String buildError(Integer httpStatus, String message, Object error) {

        if(error == null) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.getMessage(httpStatus), message);
            return JsonUtil.INSTANCE.errorDataToJson(errorResponse);
        }

        HashMap<String, Object> model = new HashMap<>();
        model.put("httpStatus", HttpStatus.getMessage(HttpStatus.BAD_REQUEST_400));
        model.put("message", message);
        model.put("errors", error);
        return JsonUtil.INSTANCE.errorDataToJson(model);
    }

    /**
     * If it does not have header, add json content type
     * @param response
     */
    public static void buildJson(Response response) {
        // if not set, set content-type as 'application/json;charset=utf-8'
        if (!response.raw().containsHeader("Content-Type")) {
            response.header("Content-Type", MediaType.JSON_UTF_8.toString());
        }
    }


}
