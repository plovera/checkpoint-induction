import Util.Credential;
import Util.Path;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;

public class Preference {

    public static Object create(Request request, Response response) throws MPException  {
        String initPoint = newPreference().getInitPoint();
        response.redirect(initPoint);
        return "";
    }

    public static Object preference(Request request, Response response) throws MPException  {
        com.mercadopago.resources.Preference pref = null;
        try {
            pref = newPreference();
        } catch (MPException e) {
            e.printStackTrace();
        }

        HashMap<String, Object> model = new HashMap<>();
        model.put("name", "Pablito");

        if(pref != null) {
            model.put("preferencia",pref.getInitPoint());
        }

        return new VelocityTemplateEngine().render(new ModelAndView(model, Path.Template.PREFERENCE));
    }


    public static Object preferencev2(Request request, Response response) throws MPException  {
        com.mercadopago.resources.Preference preference = newPreference();

        HashMap<String, Object> model = new HashMap<>();
        model.put("name", "Pablito");

        if(preference != null) {
            model.put("preferenceId", preference.getId());
        }

        return new VelocityTemplateEngine().render(new ModelAndView(model, Path.Template.PREFERENCE_V2));
    }


    public static Object processPreference(Request request, Response response) throws MPException {

        String status = request.queryParams("payment_status");
        String statusDetail = request.queryParams("payment_status_detail");

        return "Status: " + status + " - Detalle: " + statusDetail;
    }

    private static com.mercadopago.resources.Preference newPreference() throws MPException{

        MercadoPago.SDK.setClientSecret(Credential.BASIC.CLIENT_SECRET_OK);
        MercadoPago.SDK.setClientId(Credential.BASIC.CLIENT_ID_OK);


        // Create a preference object
        com.mercadopago.resources.Preference preference = new com.mercadopago.resources.Preference();
        // Create an item object
        Item item = new Item();
        item.setId("1234")
                .setTitle("Fantastic Aluminum Plate")
                .setQuantity(1)
                .setCurrencyId("ARG")
                .setUnitPrice((float) 58.02);

        // Create a payer object
        Payer payer = new Payer();
        payer.setEmail(Credential.EMAIL);
        // Setting preference properties
        preference.setPayer(payer);
        preference.appendItem(item);
        // Save and posting preference

        BackUrls backUrls = new BackUrls(
                "/success",
                "/pending",
                "/failure");

        preference.setBackUrls(backUrls);

        preference.save();

        return preference;
    }
}
