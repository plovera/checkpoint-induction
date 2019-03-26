package service;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Payer;
import data.ErrorResponse;
import data.InductionException;
import data.PaymentData;
import org.eclipse.jetty.http.HttpStatus;
import util.JsonUtil;
import util.ResponseUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.util.List;

public enum TokenizeServices {

    INSTANCE;

    /**
     * Create a Payment of MP
     * @param data
     * @return
     * @throws MPException
     */
    public static Payment createPayment(PaymentData data) throws MPException, InductionException, IOException {

        // validate the data
        List<String> errorResponses = data.validate();
        if(!errorResponses.isEmpty()) {
            throw new InductionException(ValidationUtil.INPUT_DATA_FAILED, HttpStatus.BAD_REQUEST_400, errorResponses);
        }

        // create payment
        Payment payment = newPayment(data);
        if(payment.getStatus() == null) {
            String message = payment.getLastApiResponse() != null ?
                    JsonUtil.getParam(payment.getLastApiResponse().getStringResponse(), "message")
                    : ValidationUtil.INPUT_DATA_FAILED;
            throw new InductionException(message, HttpStatus.BAD_REQUEST_400);
        }

         return payment;
    }

    private static Payment newPayment(PaymentData data) throws MPException {
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
        return payment;
    }


}
