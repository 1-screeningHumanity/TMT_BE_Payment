package com.TMT.TMT_BE_PaymentServer.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendWalletInfoDto {

    private String uuid;
    private String nickname;
    private Long won;

}
