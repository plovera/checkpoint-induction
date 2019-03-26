package service;

import com.mercadopago.exceptions.MPException;
import data.InductionException;
import data.PaymentData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TokenizeServicesTest extends ServicesTest{

    @Test
    public void cardTokenNotFound() throws InductionException, MPException, IOException {

        PaymentData data = new PaymentData("test@test.com", "1234124", "visa" , 1234.4F);
        Assertions.assertThrows(InductionException.class, () -> TokenizeServices.INSTANCE.createPayment(data));

    }


    @Test
    public void paymentValidationError() {

        Assertions.assertThrows(InductionException.class, () -> TokenizeServices.INSTANCE.createPayment(new PaymentData("test@", "1234124", "visa" , 1234.4F)));
        Assertions.assertThrows(InductionException.class, () -> TokenizeServices.INSTANCE.createPayment(new PaymentData("@test.com", "1234124", "visa" , 1234.4F)));
        Assertions.assertThrows(InductionException.class, () -> TokenizeServices.INSTANCE.createPayment(new PaymentData("test@test.com", "", "visa" , 1234.4F)));
        Assertions.assertThrows(InductionException.class, () -> TokenizeServices.INSTANCE.createPayment(new PaymentData("test@test.com", "1234124", "" , 1234.4F)));
        Assertions.assertThrows(InductionException.class, () -> TokenizeServices.INSTANCE.createPayment(new PaymentData("test@test.com", "1234124", "visa" , -1234.4F)));

    }
}
