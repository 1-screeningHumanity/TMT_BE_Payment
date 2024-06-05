package com.TMT.TMT_BE_PaymentServer.global.common.enumclass;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    SUCCESS("1"),//결제성공
    FAIL("2");//결제 실패

    private final String code;

    PaymentStatus(String code){
        this.code = code;
    }
}
