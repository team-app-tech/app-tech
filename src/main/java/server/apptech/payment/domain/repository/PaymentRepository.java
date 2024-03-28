package server.apptech.payment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.apptech.payment.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
