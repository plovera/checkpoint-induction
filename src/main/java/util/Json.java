package util;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import data.ErrorResponse;

public class Json {

    public static ObjectMapper mapper = new ObjectMapper();

    public static <T> T mapToData(String str, Class<T> tClass) throws Exception {
        return mapper.readValue(str, tClass);
    }



    public static String errorDataToJson(ErrorResponse errorResponse){
        try {
            return mapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            // Handle parsing manually.
            return "{ " +
                    "\"error\":\" " + errorResponse.getError() + " \", " +
                    "\"message\":\" " + errorResponse.getMessage() + " \" " +
                    "}";
        } catch (IOException e) {
            return "{ " +
                    "\"error\":\", " +
                    "\"message\":\" " + e.getMessage() + " \" " +
                    "}";
        }
    }

    public static String dataToJson(Object model) throws IOException {
        return mapper.writeValueAsString(model);
    }



    public static String paramJson(String paramIn) throws UnsupportedEncodingException {
        String data = URLDecoder.decode(paramIn, StandardCharsets.UTF_8.name());
        data = data.replaceAll("=", "\":\"");
        data = data.replaceAll("&", "\",\"");
        return "{\"" + data + "\"}";
    }

}
