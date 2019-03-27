import util.Credential;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;

import static spark.Spark.*;


public class Main {


    /**
     * Start the aplication
     * @param arg
     * @throws MPException
     */
    public static void main(String[] arg) throws MPException {

        // configure
        port(4567);

        // endpoint Spark
        new Endpoint().init();


        // credentials
        MercadoPago.SDK.setClientSecret(Credential.Basic.CLIENT_SECRET_OK);
        MercadoPago.SDK.setClientId(Credential.Basic.CLIENT_ID_OK);
        MercadoPago.SDK.setAccessToken(Credential.Customer.ACCESS_TOKEN);
    }
}