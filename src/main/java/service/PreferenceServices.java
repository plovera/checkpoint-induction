package service;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Address;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import data.ErrorResponse;
import data.InductionException;
import data.PreferenceData;
import org.eclipse.jetty.http.HttpStatus;
import util.Credential;
import util.ResponseUtil;
import util.ValidationUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public enum PreferenceServices {

    INSTANCE;

    /**
     * Validate the data and create preference
     * @param data
     * @return
     * @throws MPException
     */
    public static Preference createPreference(PreferenceData data) throws MPException, InductionException {

        // validate
        List<String> errorResponses = data.validate();
        if(!errorResponses.isEmpty()) {
            throw new InductionException(ValidationUtil.INPUT_DATA_FAILED, HttpStatus.BAD_REQUEST_400, errorResponses);
        }

        // create preference
        Preference preference = newPreference(data);
        if(preference == null || !ValidationUtil.hasValue(preference.getInitPoint())) {
            throw new InductionException(ValidationUtil.INPUT_DATA_FAILED, HttpStatus.BAD_REQUEST_400);
        }

        return preference;
    }


    /**
     * Create a Preference of MP
     * @param data
     * @return
     * @throws MPException
     */
    private static Preference newPreference(PreferenceData data) throws MPException{

        // Create a preference object
        Preference preference = new Preference();
        // Create an item object
        Item item = new Item();
        item.setId(data.getId())
                .setTitle(data.getTitle())
                .setQuantity(data.getQuantity())
                .setCurrencyId(data.getCurrencyId())
                .setUnitPrice(data.getUnitPrice());

        // Create a payer object
        Payer payer = new Payer();
        payer.setEmail(data.getEmail());
        Address address = new Address();
        address.setStreetName(data.getStreetName());
        address.setStreetNumber(data.getStreetNumber());
        address.setZipCode(data.getZipCode());
        payer.setAddress(address);
        // Setting preference properties
        preference.setPayer(payer);
        preference.appendItem(item);
        // Expire tomorrow
        if(data.getExpired()) {
            preference.setExpires(true);
            preference.setExpirationDateFrom(Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()));
        }
        // Save and posting preference
        preference.save();

        return preference;
    }


}
