package server.apptech.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.user.domain.User;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String amount;
//    private String approveNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Payment of(PaymentInfo tossPayment, User user){
        return Payment.builder()
                .paymentKey(tossPayment.getPaymentKey())
                .orderId(tossPayment.getOrderId())
                .orderName(tossPayment.getOrderName())
                .amount(tossPayment.getTotalAmount())
//                .approveNo(tossPayment.getCard().getApproveNo())
                .user(user).build();
    }

}
