package controller;

import data.ErrorResponse;
import data.InductionException;
import data.PaymentData;
import org.eclipse.jetty.http.HttpStatus;
import service.TokenizeServices;
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
    public static Object payment(Request request, Response response) throws IOException, MPException, InductionException {

        // map the data
        PaymentData data = RequestUtil.getData(request, PaymentData.class);

        // validate and create payment
        Payment payment = TokenizeServices.INSTANCE.createPayment(data);

        // Response
        HashMap<String, Object> model = new HashMap<>();
        model.put("status", payment.getStatus().name());
        model.put("statusDetail", payment.getStatusDetail());
        response.status(HttpStatus.OK_200);
        return model;
    }

}
