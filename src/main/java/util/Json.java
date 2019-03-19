package util;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.*;
import data.ErrorResponse;
import spark.ResponseTransformer;

public class Json implements ResponseTransformer  {


    public static ObjectMapper mapper = new ObjectMapper();

    /**
     * Map a json string to a data model of the class
     * @param str
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T mapToData(String str, Class<T> tClass) throws IOException {
        return mapper.readValue(str, tClass);
    }

    /**
     * Get the value of a field
     * @param json
     * @param field
     * @return
     * @throws IOException
     */
    public static String getParam(String json, String field) throws IOException {
        return mapper.readTree(json).get(field).asText();
    }

    /**
     * Build a string with json format with the error message
     * @param errorResponse
     * @return
     */
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

    /**
     * Render the model in a string with json format
     * @param model
     * @return
     * @throws IOException
     */
    @Override
    public String render(Object model) throws IOException {
        return mapper.writeValueAsString(model);
    }


    /**
     * decode a string to UTF-8
     * @param paramIn
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decodeMessage(String paramIn) throws UnsupportedEncodingException {
        String data = URLDecoder.decode(paramIn, StandardCharsets.UTF_8.name());
        data = data.replaceAll("=", "\":\"");
        data = data.replaceAll("&", "\",\"");
        return "{\"" + data + "\"}";
    }

}
