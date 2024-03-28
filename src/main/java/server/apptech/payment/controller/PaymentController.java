package server.apptech.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.apptech.auth.Auth;
import server.apptech.auth.AuthUser;
import server.apptech.payment.dto.PaymentRequest;
import server.apptech.payment.service.PaymentService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Payment")
public class PaymentController {


    private final PaymentService paymentService;

    @PostMapping(value = "/api/payments/confirm", consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @Operation(summary = "결제 승인", description = "결제 승인을 요청합니다.", responses = {@ApiResponse(responseCode = "200", description = "정상적으로 결제 승인")})
    public ResponseEntity<?> createAdvertisement(@Auth AuthUser authUser,
                                                 @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok()
                .body(paymentService.confirmPayment(authUser.getUserId(),  paymentRequest));
    }
}
