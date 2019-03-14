package data;

public class PaymentData {

    private String email;
    private String token;
    private String paymentMethodId;
    private Integer installments;
    private String issuer_id;
    private String description;
    private Float amount;

    public PaymentData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) { this.token = token; }

    public String getPaymentMethodId() { return paymentMethodId; }

    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    public String getIssuer_id() {
        return issuer_id;
    }

    public void setIssuer_id(String issuer_id) {
        this.issuer_id = issuer_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
