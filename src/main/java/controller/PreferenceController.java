package controller;

import com.mercadopago.resources.Preference;
import data.ErrorResponse;
import data.PreferenceData;
import util.*;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.preference.Address;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PreferenceController {

    /**
     * Create preferente
     * @param request
     * @param response
     * @return
     * @throws MPException
     * @throws IOException
     */
    public static Object create(Request request, Response response) throws MPException, IOException {

        // Map the data
        PreferenceData data = RequestUtil.getData(request, PreferenceData.class);

        // validate ans create preference
        PreferenceResult result = createPreference(data);
        if(!result.hasPreference()) {
            return result.getErrorResponse();
        }

        // preference
        Preference preference = result.getPreference();

        // Response
        HashMap<String, Object> model = new HashMap<>();
        model.put("initPoint", preference.getInitPoint());
        model.put("expires", preference.getExpires());
        model.put("expirationDateFrom", preference.getExpirationDateFrom().toString());
        response.status(HttpStatus.OK_200);
        return model;
    }

    /**
     * Create preference and redirect
     * @param request
     * @param response
     * @return
     * @throws MPException
     */
    public static Object redirect(Request request, Response response) throws MPException {

        PreferenceData data = newPreferenceDefault();

        // validate ans create preference
        PreferenceResult result = createPreference(data);
        if(!result.hasPreference()) {
            return result.getErrorResponse();
        }

        // preference
        Preference preference = result.getPreference();

        String initPoint = preference.getInitPoint();
        response.redirect(initPoint);
        response.status(HttpStatus.OK_200);
        return "";
    }


    /**
     * View v1 that shows a created default preference in different modes
     * @param request
     * @param response
     * @return
     * @throws MPException
     */
    public static Object preferenceV1(Request request, Response response) throws MPException  {

        PreferenceData data = newPreferenceDefault();

        // validate ans create preference
        PreferenceResult result = createPreference(data);

        // view model
        HashMap<String, Object> model = new HashMap<>();
        if(result.hasPreference()) {
            model.put("preference", result.getPreference().getInitPoint());
        }

        return View.render(response, model, Path.Template.PREFERENCE);
    }


    /**
     * View v2 than shows a created default preference
     * @param request
     * @param response
     * @return
     * @throws MPException
     */
    public static Object preferenceV2(Request request, Response response) throws MPException  {

        PreferenceData data = newPreferenceDefault();

        // validate ans create preference
        PreferenceResult result = createPreference(data);

        // view model
        HashMap<String, Object> model = new HashMap<>();
        if(result.hasPreference()) {
            model.put("preferenceId", result.getPreference().getId());
        }

        return View.render(response, model, Path.Template.PREFERENCE_V2);
    }


    /**
     * Set default data for one preference
     * @return
     */
    private static PreferenceData newPreferenceDefault(){
        return new PreferenceData("123", "Mesa y sillas", 1, 88.23F);
    }


    /**
     * Validate the data and create preference
     * @param data
     * @return
     * @throws MPException
     */
    private static PreferenceResult createPreference(PreferenceData data) throws MPException {
        PreferenceResult preferenceResult = new PreferenceResult();

        // validate
        List<ErrorResponse> errorResponses = data.validate();
        if(!errorResponses.isEmpty()) {
            preferenceResult.setErrorResponse(ResponseUtil.buildError(errorResponses));
            return preferenceResult;
        }

        // create preference
        Preference preference = newPreference(data);
        if(preference == null || !ValidationUtil.hasValue(preference.getInitPoint())) {
            preferenceResult.setErrorResponse(new ErrorResponse(HttpStatus.getMessage(HttpStatus.BAD_REQUEST_400), ValidationUtil.INPUT_DATA_FAILED));
            return preferenceResult;
        }

        preferenceResult.setPreference(preference);
        return preferenceResult;
    }



    /**
     * Create a Preference of MP
     * @param data
     * @return
     * @throws MPException
     */
    private static Preference newPreference(PreferenceData data) throws MPException{

        // Create a preference object
        Preference preference = new Preference();
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
        // Expire tomorrow
        preference.setExpires(true);
        preference.setExpirationDateFrom(Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()));
        // Save and posting preference
        preference.save();

        return preference;
    }


    /**
     * Status data of payment
     * @param request
     * @param response
     * @return
     * @throws MPException
     */
    public static Object process(Request request, Response response) throws MPException {

        String status = request.queryParams("payment_status");
        String statusDetail = request.queryParams("payment_status_detail");

        // Response
        HashMap<String, Object> model = new HashMap<>();
        model.put("status", status);
        model.put("statusDetail", statusDetail);
        response.status(HttpStatus.OK_200);
        return model;
    }

    /**
     * A class with a preference or validation errors
     */
    private static class PreferenceResult {
        private Preference preference;
        private Object errorResponse;

        public PreferenceResult() {
        }


        public Preference getPreference() {
            return preference;
        }

        public void setPreference(Preference preference) {
            this.preference = preference;
        }

        public Object getErrorResponse() {
            if (errorResponse != null) {
                return errorResponse;
            }
            else {
                HashMap<String, Object> model = new HashMap<>();
                model.put("httpStatus", HttpStatus.getMessage(HttpStatus.BAD_REQUEST_400));
                model.put("message", ValidationUtil.INPUT_DATA_FAILED);
                return model;
            }
        }

        public void setErrorResponse(Object errorResponse) {
            this.errorResponse = errorResponse;
        }

        public boolean hasPreference() {
            return preference != null;
        }


    }

}
