package com.TMT.TMT_BE_PaymentServer.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargeWonRequestVo {

    public int cash;

}