package server.apptech.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.Base64.*;

@Component
public class TossPaymentProvider implements PaymentProvider{

    private final String baseUri;
    private final String secretKey;
    private final String confirm = "/confirm";
    public TossPaymentProvider(@Value("${payment.provider.toss.base-uri}") String baseUri, @Value("${payment.provider.toss.secret-key}") String secretKey){
        this.baseUri = baseUri;
        this.secretKey = secretKey;
    }

    @Override
    public PaymentInfo confirmPayment(PaymentRequest paymentRequest) {

        String encodedCredentials = getEncoder().encodeToString(secretKey.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedCredentials);

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);

        RestTemplate restTemplate = new RestTemplate();
        PaymentInfo paymentInfo = restTemplate.postForObject(baseUri + confirm, requestEntity, PaymentInfo.class);

        return paymentInfo;
    }
}
