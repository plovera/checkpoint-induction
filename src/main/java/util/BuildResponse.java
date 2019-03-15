package util;

import com.google.common.net.MediaType;
import data.ErrorResponse;
import spark.Response;

public class BuildResponse {

    public static void ExceptionBuild(Response response, ErrorResponse errorResponse) {
        response.header("Content-Type", MediaType.JSON_UTF_8.toString());
        String responseBody = Json.errorDataToJson(errorResponse);
        response.body(responseBody);
        response.status(errorResponse.getHttpStatusCode());
    }
}
