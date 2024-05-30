package com.TMT.TMT_BE_PaymentServer.payment.domain;


import com.TMT.TMT_BE_PaymentServer.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PaymentLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String paymentId;

    private String payName;

    private String payMethod;

    private String totalAmount;

    private String price;


    @Builder
    public PaymentLog(String paymentId, String payName, String payMethod, String totalAmount,
            String price) {
        this.paymentId = paymentId;
        this.payName = payName;
        this.payMethod = payMethod;
        this.totalAmount = totalAmount;
        this.price = price;
    }

}
