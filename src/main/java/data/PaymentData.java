package data;

public class PaymentData {

    private String email;
    private String token;
    private String payment_method_id;
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

    public String getPayment_method_id() { return payment_method_id; }

    public void setPayment_method_id(String payment_method_id) { this.payment_method_id = payment_method_id; }

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
