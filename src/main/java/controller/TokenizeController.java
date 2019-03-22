package controller;

import data.ErrorResponse;
import data.PaymentData;
import org.eclipse.jetty.http.HttpStatus;
import util.*;
import spark.Request;
import spark.Response;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Payer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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

        // map the data
        PaymentData data = RequestUtil.getData(request, PaymentData.class);

        // validate the data
        List<ErrorResponse> errorResponses = data.validate();
        if(!errorResponses.isEmpty()) {
            response.status(HttpStatus.BAD_REQUEST_400);
            return ResponseUtil.buildError(errorResponses);
        }

        // create payment and then validate
        Payment payment = savePayment(data);
        if(payment.getStatus() == null) {
            response.status(HttpStatus.BAD_REQUEST_400);
            String message = payment.getLastApiResponse() != null ? JsonUtil.getParam(payment.getLastApiResponse().getStringResponse(), "message") : ValidationUtil.INPUT_DATA_FAILED;
            return new ErrorResponse(HttpStatus.getMessage(HttpStatus.BAD_REQUEST_400), message);
        }

        // Response
        HashMap<String, Object> model = new HashMap<>();
        model.put("status", payment.getStatus().name());
        model.put("statusDetail", payment.getStatusDetail());
        response.status(HttpStatus.OK_200);
        return model;
    }

    /**
     * Create a Payment of MP
     * @param data
     * @return
     * @throws MPException
     */
    private static Payment savePayment(PaymentData data) throws MPException {
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
        return payment.save();
    }

}
