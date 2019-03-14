package data;

public class PreferenceData {
    private String id;
    private String title;
    private Integer quantity;
    private String currencyId;
    private Float unitPrice;
    private String StreetName;
    private Integer StreetNumber;
    private String zipCode;

    public PreferenceData() {
    }

    public PreferenceData(String id, String title, int quantity, String currencyId, float unitPrice) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.currencyId = currencyId;
        this.unitPrice = unitPrice;
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

    public boolean isValid() {
        return !id.isEmpty() && !title.isEmpty() && quantity != null && quantity > 0 && !currencyId.isEmpty() && unitPrice != null && unitPrice > 0;
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
}