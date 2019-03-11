import Util.Credential;
import Util.Path;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;
import com.mercadopago.*;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Payer;

import java.util.HashMap;

public class Tokenize {


    public static Object tokenize(Request request, Response response) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("name", "Pablito");
        return new VelocityTemplateEngine().render(new ModelAndView(model, Path.Template.TOKENIZE));
    }

    public static Object payment(Request req, Response res) throws MPException {

        MercadoPago.SDK.setAccessToken(Credential.CUSTOMER.ACCESS_TOKEN);


        String email = req.queryParams("email");
        String token = req.queryParams("token");
        String paymentMethodId = req.queryParams("paymentMethodId");
        int installments = Integer.parseInt(req.queryParams("installments"));


        Payment payment = new Payment();
        try {
            payment.setTransactionAmount(123F)
                    .setToken(token)
                    .setDescription("prueba")
                    .setInstallments(installments)
                    .setPaymentMethodId(paymentMethodId)
                    .setPayer(new Payer()
                            .setEmail(email));
// Save and posting the payment
             payment.save();
        }
        catch(Exception e){

        }
//...
        String status = payment.getStatus() != null ? payment.getStatus().name() : null;
        String statusDetail = payment.getStatusDetail();

        return "Status: " + status + " - Detalle: " + statusDetail;
    }

    public static Object tokenizev2(Request request, Response response) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("name", "Pablito");
        return new VelocityTemplateEngine().render(new ModelAndView(model, Path.Template.TOKENIZE_V2));
    }



    public static Object processPay(Request request, Response response) throws MPException {

        String token = request.queryParams("token");
        String paymentMethodId = request.queryParams("payment_method_id");
        String issuerId = request.queryParams("issuer_id");

        String installmentsString = request.queryParams("installments");
        if(installmentsString == null)
            return "Sin parametros";
        int installments = Integer.parseInt(installmentsString);


        MercadoPago.SDK.setAccessToken(Credential.CUSTOMER.ACCESS_TOKEN);
//...
        Payment payment = new Payment();
        payment.setTransactionAmount(123F)
                .setToken(token)
                .setDescription("Descripcion")
                .setInstallments(installments)
                .setPaymentMethodId(paymentMethodId)
                .setIssuerId(issuerId)
                .setPayer(new Payer()
                        .setEmail(Credential.EMAIL));
// Guarda y postea el pago
        payment.save();

//...
        String status = payment.getStatus() != null ? payment.getStatus().name() : null;
        String statusDetail = payment.getStatusDetail();

        return "Status: " + status + " - Detalle: " + statusDetail;
    }
}
