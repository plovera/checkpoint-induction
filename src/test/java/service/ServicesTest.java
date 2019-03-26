package service;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import org.junit.jupiter.api.BeforeEach;
import util.Credential;

public class ServicesTest {

    @BeforeEach
    public void beforeSetCredential() throws MPException {

        /* credentials */
        MercadoPago.SDK.setClientSecret(Credential.Basic.CLIENT_SECRET_OK);
        MercadoPago.SDK.setClientId(Credential.Basic.CLIENT_ID_OK);
        MercadoPago.SDK.setAccessToken(Credential.Customer.ACCESS_TOKEN);
    }
}
