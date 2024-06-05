package com.TMT.TMT_BE_PaymentServer.payment.domain;


import com.TMT.TMT_BE_PaymentServer.global.common.enumclass.PayName;
import com.TMT.TMT_BE_PaymentServer.global.common.enumclass.PaymentStatus;
import com.TMT.TMT_BE_PaymentServer.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private String orderNum; //주문번호

    private String uuid; //uuid

    @Enumerated(EnumType.STRING)
    private PayName payName; //결제플랫폼

    private String payMethod; //결제수단(카드,현금)

    private int totalAmount; //총액

    private String itemName; //상품이름

    private int quantity; //상품수량

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;//결제상태

    @Builder
    public PaymentLog(Long paymentId, String orderNum, String uuid, PayName payName,
            String payMethod, int totalAmount, String itemName, int quantity,
            PaymentStatus paymentStatus) {

        this.paymentId = paymentId;
        this.orderNum = orderNum;
        this.uuid = uuid;
        this.payName = payName;
        this.payMethod = payMethod;
        this.totalAmount = totalAmount;
        this.itemName = itemName;
        this.quantity = quantity;
        this.paymentStatus = paymentStatus;

    }
}
