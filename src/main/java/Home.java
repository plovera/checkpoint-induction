
import com.mercadopago.exceptions.MPException;
import spark.Request;
import spark.Response;
import util.Path;
import util.View;

import java.util.HashMap;

public class Home {


    public static Object home(Request req, Response res) throws MPException {

        return View.render(res, Path.Template.HOME);
    }
}
