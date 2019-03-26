package controller;

import com.mercadopago.resources.Preference;
import data.InductionException;
import data.PreferenceData;
import service.PreferenceServices;
import util.*;
import com.mercadopago.exceptions.MPException;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;

public class PreferenceController {

    /**
     * Create preferente
     * @param request
     * @param response
     * @return
     * @throws MPException
     * @throws IOException
     */
    public static Object create(Request request, Response response) throws MPException, IOException, InductionException {

        // Map the data
        PreferenceData data = RequestUtil.getData(request, PreferenceData.class);

        // validate and create preference
        Preference preference = PreferenceServices.INSTANCE.createPreference(data);

        // Response
        HashMap<String, Object> model = new HashMap<>();
        model.put("initPoint", preference.getInitPoint());
        model.put("expires", preference.getExpires());
        model.put("expirationDateFrom", ValidationUtil.hasValue(preference.getExpirationDateFrom()) ?
                preference.getExpirationDateFrom().toString()
                : null);
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
    public static Object redirect(Request request, Response response) throws MPException, InductionException {

        PreferenceData data = newPreferenceDefault();

        // validate ans create preference
        Preference preference = PreferenceServices.INSTANCE.createPreference(data);

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
    public static Object preferenceV1(Request request, Response response) throws MPException, InductionException {

        PreferenceData data = newPreferenceDefault();
        data.setExpired(true);

        // validate ans create preference
        Preference preference = PreferenceServices.INSTANCE.createPreference(data);

        // view model
        HashMap<String, Object> model = new HashMap<>();
        model.put("preference", preference.getInitPoint());
        response.status(HttpStatus.OK_200);
        return View.render(response, model, Path.Template.PREFERENCE);
    }


    /**
     * View v2 than shows a created default preference
     * @param request
     * @param response
     * @return
     * @throws MPException
     */
    public static Object preferenceV2(Request request, Response response) throws MPException, InductionException {

        PreferenceData data = newPreferenceDefault();

        // validate ans create preference
        Preference preference = PreferenceServices.INSTANCE.createPreference(data);

        // view model
        HashMap<String, Object> model = new HashMap<>();
        model.put("preferenceId", preference.getId());
        response.status(HttpStatus.OK_200);
        return View.render(response, model, Path.Template.PREFERENCE_V2);
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
     * Set default data for one preference
     * @return
     */
    private static PreferenceData newPreferenceDefault(){
        return new PreferenceData("123", "Mesa y sillas", 1, 88.23F, Credential.EMAIL);
    }




}
