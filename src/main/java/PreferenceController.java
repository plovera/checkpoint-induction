import com.google.common.net.MediaType;
import data.PreferenceData;
import util.Credential;
import util.Path;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.preference.Address;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import org.eclipse.jetty.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;
import com.fasterxml.jackson.databind.*;
import util.View;

import java.io.IOException;
import java.util.HashMap;

public class PreferenceController {



    public static Object preference(Request request, Response response) throws MPException, IOException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            PreferenceData data = mapper.readValue(request.body(), PreferenceData.class);

            if(!data.isValid()) {
                response.status(HttpStatus.BAD_REQUEST_400);
                return HttpStatus.getMessage(HttpStatus.BAD_REQUEST_400);
            }

            String initPoint = newPreference(data).getInitPoint();
            response.status(HttpStatus.OK_200);
            return "";

        } catch(Exception e){
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return HttpStatus.getMessage(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }

    }


    public static Object create(Request request, Response response) throws MPException {

            PreferenceData data = newPreferenceDefault();
            com.mercadopago.resources.Preference pref = newPreference(data);

            if(pref != null && !pref.getInitPoint().isEmpty()) {
                String initPoint = newPreference(data).getInitPoint();
                response.redirect(initPoint);
                return "";
            }

            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return HttpStatus.getMessage(HttpStatus.INTERNAL_SERVER_ERROR_500);

    }


    public static Object preferenceV1(Request request, Response response) throws MPException  {

        PreferenceData data = newPreferenceDefault();
        com.mercadopago.resources.Preference pref = newPreference(data);

        HashMap<String, Object> model = new HashMap<>();

        if(pref != null) {
            model.put("preference", pref.getInitPoint());
        }

        return View.render(response, model, Path.Template.PREFERENCE);
    }


    public static Object preferenceV2(Request request, Response response) throws MPException  {

        PreferenceData data = newPreferenceDefault();
        com.mercadopago.resources.Preference preference = newPreference(data);

        HashMap<String, Object> model = new HashMap<>();
        if(preference != null) {
            model.put("preferenceId", preference.getId());
        }

        return View.render(response, model, Path.Template.PREFERENCE_V2);
    }


    private static com.mercadopago.resources.Preference newPreference(PreferenceData data) throws MPException{

        // Create a preference object
        com.mercadopago.resources.Preference preference = new com.mercadopago.resources.Preference();
        // Create an item object
        Item item = new Item();
        item.setId(data.getId())
                .setTitle(data.getTitle())
                .setQuantity(data.getQuantity())
                .setCurrencyId(data.getCurrencyId())
                .setUnitPrice(data.getUnitPrice());

        // Create a payer object
        Payer payer = new Payer();
        payer.setEmail(Credential.EMAIL);
        Address address = new Address();
        address.setStreetName(data.getStreetName());
        address.setStreetNumber(data.getStreetNumber());
        address.setZipCode(data.getZipCode());
        payer.setAddress(address);
        // Setting preference properties
        preference.setPayer(payer);
        preference.appendItem(item);
        // Save and posting preference
        preference.save();

        return preference;
    }


    private static PreferenceData newPreferenceDefault(){
        return new PreferenceData("123", "Mesa y sillas", 1, "ARG", 88.23F);
    }


    public static Object process(Request request, Response response) throws MPException {

        String status = request.queryParams("payment_status");
        String statusDetail = request.queryParams("payment_status_detail");

        return "Status: " + status + " - Detalle: " + statusDetail;
    }


}
