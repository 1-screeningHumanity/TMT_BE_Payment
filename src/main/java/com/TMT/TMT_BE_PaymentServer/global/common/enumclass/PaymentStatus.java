package com.TMT.TMT_BE_PaymentServer.global.common.enumclass;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    READY("1"),//결제대기
    SUCCESS("2"),//결제성공
    FAIL("3");//결제 실패

    private final String code;

    PaymentStatus(String code){
        this.code = code;
    }
}
