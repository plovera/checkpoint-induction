package util;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import data.ErrorResponse;
import spark.ResponseTransformer;

public class Json implements ResponseTransformer  {

    public static ObjectMapper mapper = new ObjectMapper();

    public static <T> T mapToData(String str, Class<T> tClass) throws IOException {
        return mapper.readValue(str, tClass);
    }

    public static String getParam(String json, String field) throws IOException {
        return mapper.readTree(json).get(field).asText();
    }

    public static String errorDataToJson(ErrorResponse errorResponse){
        try {
            return mapper.writeValueAsString(errorResponse);
        } catch (IOException e) {
            return "{ " +
                    "\"error\":\", " +
                    "\"message\":\" " + e.getMessage() + " \" " +
                    "}";
        }
    }

    @Override
    public String render(Object model) throws Exception {
        return mapper.writeValueAsString(model);
    }


    public static String decodeToJson(String paramIn) throws UnsupportedEncodingException {
        String data = URLDecoder.decode(paramIn, StandardCharsets.UTF_8.name());
        data = data.replaceAll("=", "\":\"");
        data = data.replaceAll("&", "\",\"");
        return "{\"" + data + "\"}";
    }

}
