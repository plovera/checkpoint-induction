import Util.Path;
import com.mercadopago.exceptions.MPException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;

public class Home {


    public static Object home(Request req, Response res) throws MPException {
        HashMap<String, Object> model = new HashMap<>();
        model.put("name", "Pablito");
        return new VelocityTemplateEngine().render(new ModelAndView(model, Path.Template.HOME));
    }
}
