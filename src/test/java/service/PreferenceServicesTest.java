package service;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import data.InductionException;
import data.PreferenceData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PreferenceServicesTest extends ServicesTest{


    @Test
    public void createPreferenceCorrect() throws MPException, InductionException {

        PreferenceData data = new PreferenceData("id", "title", 1, 34.5F, "test@test.com");
        Preference preference = PreferenceServices.INSTANCE.createPreference(data);

        Assertions.assertNotNull(preference);
        Assertions.assertNotNull(preference.getInitPoint());
    }

    @Test
    public void preferenceValidationError() throws MPException, InductionException {

        Assertions.assertThrows(InductionException.class, () -> PreferenceServices.INSTANCE.createPreference(new PreferenceData()));
        Assertions.assertThrows(InductionException.class, () -> PreferenceServices.INSTANCE.createPreference(new PreferenceData("", "title", 1, 34.5F, "test@test.com")));
        Assertions.assertThrows(InductionException.class, () -> PreferenceServices.INSTANCE.createPreference(new PreferenceData("id", null, 1, 34.5F, "test@test.com")));
        Assertions.assertThrows(InductionException.class, () -> PreferenceServices.INSTANCE.createPreference(new PreferenceData("id", "title", -1, 34.5F, "test@test.com")));
        Assertions.assertThrows(InductionException.class, () -> PreferenceServices.INSTANCE.createPreference(new PreferenceData("id", "title", 1, -34.5F, "test@test.com")));
        Assertions.assertThrows(InductionException.class, () -> PreferenceServices.INSTANCE.createPreference(new PreferenceData("", "title", 1, 34.5F, "test@")));
        Assertions.assertThrows(InductionException.class, () -> PreferenceServices.INSTANCE.createPreference(new PreferenceData("id", "title", 1, 34.5F, "@test.com")));
    }

    @Test
    public void notCreatedPreference() {
        // preference -> init point = null
        Assertions.assertThrows(InductionException.class, () -> PreferenceServices.INSTANCE.createPreference(new PreferenceData("id", "title" , 1256123, 34.5F, "test@test.com")));

    }



}
