package com.TMT.TMT_BE_PaymentServer.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeWonResponseDto {

    public Long won;
    public void getwon(Long won){
        this.won = won;
    }

}