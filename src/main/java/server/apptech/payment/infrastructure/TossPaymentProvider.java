package server.apptech.payment.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.InvalidPaymentException;
import server.apptech.payment.dto.PaymentRequest;
import server.apptech.payment.domain.PaymentProvider;

import static java.util.Base64.*;

@Component
public class TossPaymentProvider implements PaymentProvider {

    private final String baseUri;
    private final String secretKey;
    private final String CONFIRM = "/confirm";
    public TossPaymentProvider(@Value("${payment.provider.toss.test.base-uri}") String baseUri, @Value("${payment.provider.toss.test.secret-key}") String secretKey){
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
        ResponseEntity<PaymentInfo> responseEntity = restTemplate.postForEntity(baseUri + CONFIRM, requestEntity, PaymentInfo.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return responseEntity.getBody();
        }
        throw new InvalidPaymentException(ExceptionCode.INVALID_PAYMENT_REQUEST);
    }
}
