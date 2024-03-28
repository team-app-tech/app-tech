package server.apptech.payment;

import org.springframework.web.client.RestTemplate;

public interface PaymentProvider {

    RestTemplate restTemplate = new RestTemplate();

    public PaymentInfo confirmPayment(PaymentRequest paymentRequest);

}
