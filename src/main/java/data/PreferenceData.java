package data;

import util.ValidationUtil;

import java.util.*;

public class PreferenceData implements Validator {
    private String id;
    private String title;
    private Integer quantity;
    private String currencyId;
    private Float unitPrice;
    private String StreetName;
    private Integer StreetNumber;
    private String zipCode;
    private Boolean expired;
    private String email;

    public PreferenceData() {
    }

    public PreferenceData(String id, String title, int quantity, float unitPrice, String email) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public Integer getStreetNumber() {
        return StreetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        StreetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean getExpired() { return expired != null && expired; }

    public void setExpired(Boolean expired) { this.expired = expired; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        errors.add(ValidationUtil.validateHasValue(id, "id"));
        errors.add(ValidationUtil.validateHasValue(title, "title"));
        errors.add(ValidationUtil.validateHasValue(quantity, "quantity"));
        errors.add(ValidationUtil.validateHasValue(unitPrice, "unitPrice"));
        errors.add(ValidationUtil.validateIsEmail(email, "email"));

        errors.removeAll(Collections.singleton(null));
        return errors;
    }
}