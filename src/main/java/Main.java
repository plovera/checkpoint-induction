import Util.Path;
import com.mercadopago.exceptions.MPException;

import static spark.Spark.*;


public class Main {



    public static void main(String[] arg) throws MPException {


        get("/",  Home::home);
        get(Path.Web.HOME,  Home::home);
        get(Path.Web.CREATE_PREFERENCE, Preference::create);
        get(Path.Web.PREFERENCE, Preference::preference);
        get(Path.Web.TOKENIZE_V1, Tokenize::tokenize);
        get(Path.Web.TOKENIZE_V2, Tokenize::tokenizev2);
        get(Path.Web.PREFERENCE_V2, Preference::preferencev2);

        post(Path.Web.PAYMENT, Tokenize::payment);
        post(Path.Web.PROCESS_PAY, Tokenize::processPay);
        post(Path.Web.PROCESS_PREFERENCE, Preference::processPreference);



    }


}
