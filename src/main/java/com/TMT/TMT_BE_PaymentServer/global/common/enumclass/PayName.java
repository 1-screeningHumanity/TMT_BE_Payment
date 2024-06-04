package com.TMT.TMT_BE_PaymentServer.global.common.enumclass;

import lombok.Getter;

@Getter
public enum PayName  {

    KakaoPay("1"),//결제성공
    TossPay("2"),//결제 실패
    KG("3");//KG
    private final String code;

    PayName(String code){
        this.code = code;
    }
}
