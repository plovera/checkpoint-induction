package service;

import com.mercadopago.resources.Payment;

public enum TokenizeServices {

    INSTANCE;

    public  Payment createPayment() {
        return new Payment();
    }


}
