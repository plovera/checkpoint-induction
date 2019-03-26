package util;

import data.PaymentData;
import data.PreferenceData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;


public class DataMapTest {

    @Test
    void DataMapException() {
        Assertions.assertThrows(IOException.class, ()-> JsonUtil.mapToData("", PaymentData.class));
        Assertions.assertThrows(IOException.class, ()-> JsonUtil.mapToData("{ alog:algo:algo }", PaymentData.class));
    }


    @Test
    public void PaymentDataMap() throws IOException {

        String body = "{\n" +
                "\t\"token\": \"e08f052f7a5577e1753c17329f7e7fdb\",\n" +
                "\t\"payment_method_id\": \"visa\",\n" +
                "\t\"amount\": 123.45,\n" +
                "\t\"installments\": \"1\",\n" +
                "\t\"email\": \"test_user_61906920@testuser.com\",\n" +
                "\t\"description\": \"prueba\"\n" +
                "}";

        PaymentData paymentData = JsonUtil.mapToData(body, PaymentData.class);

        Assertions.assertNotNull(paymentData);
        Assertions.assertEquals("e08f052f7a5577e1753c17329f7e7fdb", paymentData.getToken());
        Assertions.assertEquals("visa", paymentData.getPayment_method_id());
        Assertions.assertEquals(123.45F, paymentData.getAmount().floatValue());
        Assertions.assertEquals(1, paymentData.getInstallments().intValue());
        Assertions.assertEquals("test_user_61906920@testuser.com", paymentData.getEmail());
        Assertions.assertEquals("prueba", paymentData.getDescription());

    }


    @Test
    public void PreferenceDataMap() throws IOException {

        String body = "{\n" +
                "\t\"id\": \"123\",\n" +
                "\t\"title\": \"preferencia\",\n" +
                "\t\"quantity\": 1,\n" +
                "\t\"currencyId\" : \"ARG\",\n" +
                "\t\"unitPrice\" : 82.25\n" +
                "}";

        PreferenceData preferenceData = JsonUtil.mapToData(body, PreferenceData.class);

        Assertions.assertNotNull(preferenceData);
        Assertions.assertEquals("123", preferenceData.getId());
        Assertions.assertEquals("preferencia", preferenceData.getTitle());
        Assertions.assertEquals( 1, preferenceData.getQuantity().intValue());
        Assertions.assertEquals("ARG", preferenceData.getCurrencyId());
        Assertions.assertEquals(82.25F, preferenceData.getUnitPrice().floatValue());



    }
}
