package util;

import com.google.common.net.MediaType;
import data.ErrorResponse;
import org.eclipse.jetty.http.HttpStatus;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class ResponseUtil {

    /**
     * Build exception message with JsonUtil format
     * @param response
     * @param httpStatus
     * @param message
     */
    public static void buildException(Response response, Integer httpStatus, String message) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.getMessage(httpStatus), message);
        String responseBody = JsonUtil.errorDataToJson(errorResponse);
        response.body(responseBody);
        response.status(httpStatus);
        response.header("Content-Type", MediaType.JSON_UTF_8.toString());
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


    /**
     * Build model for error message
     * @param errorResponses
     * @return
     */
    public static HashMap<String, Object> buildError(List<ErrorResponse> errorResponses) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("httpStatus", HttpStatus.getMessage(HttpStatus.BAD_REQUEST_400));
        model.put("message", ValidationUtil.INPUT_DATA_FAILED);
        model.put("errors", errorResponses);
        return model;
    }
}
