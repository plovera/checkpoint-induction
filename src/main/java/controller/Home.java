package controller;

import spark.Request;
import spark.Response;
import util.Path;
import util.View;

public class Home {


    /**
     * Home View
     * @param req
     * @param res
     * @return
     */
    public static Object home(Request req, Response res)  {

        return View.render(res, Path.Template.HOME);
    }
}
