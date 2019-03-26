package data;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentDataTest {

    @Test
    public void validateTruePaymentData() {
        PaymentData data = new PaymentData("test@test.com", "123123123","visa",53.4F );

        List<String> validation = data.validate();

        assertEquals(false, validation.isEmpty());
    }

    @Test
    public void emptyPaymentData() {
        PaymentData data = new PaymentData();
        List<String> validation = data.validate();
        assertEquals(false, validation.isEmpty());
        assertEquals(true, validation.stream().anyMatch(i -> i.contains("email")));
        assertEquals(true, validation.stream().anyMatch(i -> i.contains("token")));
        assertEquals(true, validation.stream().anyMatch(i -> i.contains("payment_method_id")));
        assertEquals(true, validation.stream().anyMatch(i -> i.contains("amount")));
    }

    @Test
    public void wrongEmailPaymentData() {
        PaymentData data = new PaymentData("test", "123123123","visa",53.4F );
        List<String> validation = data.validate();
        assertEquals(false, validation.isEmpty());
        assertEquals(true, validation.stream().anyMatch(i -> i.contains("email")));

    }
}
