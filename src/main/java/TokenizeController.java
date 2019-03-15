import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import data.ErrorResponse;
import data.PaymentData;
import data.PreferenceData;
import org.eclipse.jetty.http.HttpStatus;
import util.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;
import com.mercadopago.*;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Payer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class TokenizeController {


    /**
     * View of tokenize v1
     * @param request
     * @param response
     * @return
     */
    public static Object tokenize(Request request, Response response) {

        return View.render(response, Path.Template.TOKENIZE);
    }


    /**
     * View of tokenize v2
     * @param request
     * @param response
     * @return
     */
    public static Object tokenizev2(Request request, Response response) {
        return View.render(response, Path.Template.TOKENIZE_V2);
    }


    /**
     * Make a payment with a token
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws MPException
     */
    public static Object payment(Request request, Response response) throws IOException, MPException {

        String body = request.body();

        // if content type is application/x-www-form-urlencoded, decode to json
        String contentType = request.contentType();
        if(contentType.equals(MediaType.FORM_DATA.toString())) {
            body = Json.decodeToJson(body);
        }

        // mapping request data
        PaymentData data = Json.mapToData(body, PaymentData.class);

        // create payment
        Payment payment = new Payment();
        payment.setTransactionAmount(data.getAmount())
                .setToken(data.getToken())
                .setDescription(data.getDescription())
                .setInstallments(data.getInstallments())
                .setPaymentMethodId(data.getPayment_method_id())
                .setIssuerId(data.getIssuer_id())
                .setPayer(new Payer()
                        .setEmail(data.getEmail()));
        // Save payment
        payment.save();

        // if status is null, return error message
        if(payment.getStatus() == null) {
            String message = payment.getLastApiResponse() != null ? Json.getParam(payment.getLastApiResponse().getStringResponse(), "message") : "Input data failed";
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST_400, HttpStatus.getMessage(HttpStatus.BAD_REQUEST_400), message);
            response.status(errorResponse.getHttpStatusCode());
            return errorResponse;
        }

        // Response
        HashMap<String, Object> model = new HashMap<>();
        model.put("status", payment.getStatus().name());
        model.put("statusDetail", payment.getStatusDetail());
        response.status(HttpStatus.OK_200);
        return model;
    }

}
