package data;


import java.util.Date;

public class PaymentResponse {

     private String initPoint;
     private boolean expires;
     private String expirationDateFrom;

    public PaymentResponse() {
    }

    public PaymentResponse(String initPoint, boolean expires, String expirationDateFrom) {
        this.initPoint = initPoint;
        this.expires = expires;
        this.expirationDateFrom = expirationDateFrom;
    }

    public String getInitPoint() {
        return initPoint;
    }

    public void setInitPoint(String initPoint) {
        this.initPoint = initPoint;
    }

    public boolean isExpires() {
        return expires;
    }

    public void setExpires(boolean expires) {
        this.expires = expires;
    }

    public String getExpirationDateFrom() {
        return expirationDateFrom;
    }

    public void setExpirationDateFrom(String expirationDateFrom) {
        this.expirationDateFrom = expirationDateFrom;
    }
}
