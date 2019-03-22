package util;

import com.google.common.net.MediaType;
import spark.Request;

import java.io.IOException;

public class RequestUtil {

    /**
     * Build a data object with the body parameters
     * @param request
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T getData(Request request, Class<T> tClass) throws IOException {
        String body = request.body();

        // if content type is application/x-www-form-urlencoded, decode to json
        if(request.contentType().equals(MediaType.FORM_DATA.toString())) {
            body = JsonUtil.decodeMessage(body);
        }

        // mapping request data
        return JsonUtil.mapToData(body, tClass);
    }

}

