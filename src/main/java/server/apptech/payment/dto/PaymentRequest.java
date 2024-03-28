package server.apptech.payment.dto;

import lombok.Getter;

@Getter
public class PaymentRequest {

    private String paymentKey;
    private String orderId;
    private Integer amount;

}
