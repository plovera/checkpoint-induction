package util;

import com.google.common.net.MediaType;
import spark.ModelAndView;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class View {

    public static String render(Response response, Map<String, Object> model, String path) {

        model.put("name", "pablito");
        response.header("Content-Type", MediaType.HTML_UTF_8.toString());
        return new VelocityTemplateEngine().render(new ModelAndView(model, path));
    }

    public static String render(Response response, String path) {
        return View.render(response, new HashMap<>(), path);
    }
}
