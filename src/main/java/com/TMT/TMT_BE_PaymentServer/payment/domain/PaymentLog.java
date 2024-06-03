package com.TMT.TMT_BE_PaymentServer.payment.domain;


import com.TMT.TMT_BE_PaymentServer.global.common.enumclass.PaymentStatus;
import com.TMT.TMT_BE_PaymentServer.global.entity.BaseEntity;
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
    private Long paymentId;

    private String orderNum;

    private String uuid;

    private String payName;

    private String payMethod;

    private String totalAmount;

    private String price;

    private PaymentStatus paymentStatus;


    @Builder
    public PaymentLog(Long paymentId, String orderNum, String uuid, String payName, String payMethod, String totalAmount,
            String price, PaymentStatus paymentStatus ) {
        this.paymentId = paymentId;
        this.orderNum = orderNum;
        this.payName = payName;
        this.uuid = uuid;
        this.payMethod = payMethod;
        this.totalAmount = totalAmount;
        this.price = price;
        this.paymentStatus = paymentStatus;
    }

}
