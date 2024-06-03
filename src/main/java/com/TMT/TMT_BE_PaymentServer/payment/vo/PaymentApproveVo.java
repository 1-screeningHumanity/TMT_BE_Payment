package com.TMT.TMT_BE_PaymentServer.payment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentApproveVo {

    private String tid;
    private String approval_url;

}
