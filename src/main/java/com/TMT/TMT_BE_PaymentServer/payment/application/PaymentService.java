package com.TMT.TMT_BE_PaymentServer.payment.application;

import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayReadyResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentReadyVo;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PaymentService {
    KaKaoPayReadyResponseDto KakaopayRequest(PaymentReadyVo paymentStockInfoVo, String uuid)
            throws JsonProcessingException;
}
