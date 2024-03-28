package server.apptech.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.exception.RestApiException;
import server.apptech.payment.infrastructure.PaymentInfo;
import server.apptech.payment.domain.PaymentProvider;
import server.apptech.payment.dto.PaymentRequest;
import server.apptech.payment.domain.Payment;
import server.apptech.payment.domain.repository.PaymentRepository;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.User;


@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final PaymentProvider paymentProvider;
    public Long confirmPayment(Long userId, PaymentRequest paymentRequest) {

        PaymentInfo paymentInfo = paymentProvider.confirmPayment(paymentRequest);

        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ExceptionCode.NOT_FOUND_USER_ID));

        return paymentRepository.save(Payment.of(paymentInfo, user)).getId();
    }
}
