package com.TMT.TMT_BE_PaymentServer.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CashDto {

    private int cash;

    public void getcash(int cash){

        this.cash = cash;
    }

}