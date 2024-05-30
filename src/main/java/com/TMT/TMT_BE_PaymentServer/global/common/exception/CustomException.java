package com.TMT.TMT_BE_PaymentServer.global.common.exception;


import com.TMT.TMT_BE_PaymentServer.global.common.response.BaseResponseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final BaseResponseCode status;

    public CustomException(BaseResponseCode status) {
        this.status = status;
    }
}