package server.apptech.payment.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInfo {

    private String paymentKey;
    private String orderId;
    private String orderName;
    private String method;
    private String totalAmount;
    private Card card;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Card{
        private String approveNo;
    }
}