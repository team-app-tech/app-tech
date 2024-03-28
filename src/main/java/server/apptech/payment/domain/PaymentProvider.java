package server.apptech.payment.domain;

import org.springframework.web.client.RestTemplate;
import server.apptech.payment.infrastructure.PaymentInfo;
import server.apptech.payment.dto.PaymentRequest;

public interface PaymentProvider {

    RestTemplate restTemplate = new RestTemplate();

    public PaymentInfo confirmPayment(PaymentRequest paymentRequest);

}
