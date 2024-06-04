package com.TMT.TMT_BE_PaymentServer.payment.application;

import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayApproveResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayReadyResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentApproveVo;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentReadyVo;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PaymentService {
    KaKaoPayReadyResponseDto KakaopayRequest(PaymentReadyVo paymentStockInfoVo, String uuid)
            throws JsonProcessingException;

    //approve header
    KaKaoPayApproveResponseDto kakaoPayApprove(PaymentApproveVo paymentApproveVo, String uuid);
}
