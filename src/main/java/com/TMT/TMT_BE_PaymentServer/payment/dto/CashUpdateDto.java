package com.TMT.TMT_BE_PaymentServer.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CashUpdateDto {

    private String uuid;
    private int cash;

    public void getCashUpdateDto(String uuid,int cash){
        this.uuid = uuid;
        this.cash = cash;
    }

}
