package com.TMT.TMT_BE_PaymentServer.payment.infrastructure;


import com.TMT.TMT_BE_PaymentServer.payment.domain.PaymentLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentLog, Long> {

}
