package com.TMT.TMT_BE_PaymentServer.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeWonResponseDto {

    public int won;
    public void getwon(int won){
        this.won = won;
    }

}