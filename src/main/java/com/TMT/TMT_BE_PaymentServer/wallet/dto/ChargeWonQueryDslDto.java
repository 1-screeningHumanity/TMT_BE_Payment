package com.TMT.TMT_BE_PaymentServer.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeWonQueryDslDto {

    private String uuid;
    private int won;
    private int cash;

}