package com.TMT.TMT_BE_PaymentServer.payment.presentation;


import com.TMT.TMT_BE_PaymentServer.global.common.response.BaseResponse;
import com.TMT.TMT_BE_PaymentServer.global.common.token.DecodingToken;
import com.TMT.TMT_BE_PaymentServer.payment.application.PaymentServiceImp;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentReadyVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final DecodingToken decodingToken;
    private final PaymentServiceImp paymentServiceImp;
    private final ModelMapper modelMapper;




    @PostMapping("/payments/kakaopay")
    public BaseResponse<Void> kakaoPayRequest(@RequestHeader("Authorization")String jwt,
            @RequestBody PaymentReadyVo paymentStockInfoVo) {

        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!");

        String uuid = decodingToken.getUuid(jwt);
        paymentServiceImp.KakaopayRequest(paymentStockInfoVo, uuid);
        return new BaseResponse<>();
    }



}
