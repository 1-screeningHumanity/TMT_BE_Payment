package com.TMT.TMT_BE_PaymentServer.payment.application;

import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayApproveResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayReadyResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.PaymentLogResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentApproveVo;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentReadyVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface PaymentService {
    KaKaoPayReadyResponseDto KakaopayRequest(PaymentReadyVo paymentStockInfoVo, String uuid)
            throws JsonProcessingException;

    //approve header
    KaKaoPayApproveResponseDto kakaoPayApprove(PaymentApproveVo paymentApproveVo, String uuid);

    //paymentLog저장
    void paymentLogSave(KaKaoPayApproveResponseDto result, String orderNum,
            String uuid) //결제 대기일때도 일단 DB에 저장
    ;

    List<PaymentLogResponseDto> paymentlog(String uuid);
}
