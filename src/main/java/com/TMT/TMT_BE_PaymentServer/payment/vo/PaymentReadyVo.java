package com.TMT.TMT_BE_PaymentServer.payment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReadyVo {

    private String itemName; //상품명
    private  int quantity; //상품수량
    private int totalAmount; //상품총액


}
