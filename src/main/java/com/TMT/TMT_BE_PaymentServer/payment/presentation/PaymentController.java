package com.TMT.TMT_BE_PaymentServer.payment.presentation;


import com.TMT.TMT_BE_PaymentServer.global.common.response.BaseResponse;
import com.TMT.TMT_BE_PaymentServer.global.common.token.DecodingToken;
import com.TMT.TMT_BE_PaymentServer.payment.application.PaymentServiceImp;
import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayApproveResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayReadyResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.PaymentLogResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentApproveVo;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentReadyVo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


//VO - DTO 통일하기
@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final DecodingToken decodingToken;
    private final PaymentServiceImp paymentServiceImp;
//    private final ModelMapper modelMapper;

    @PostMapping("/kakaopay")
    public BaseResponse<KaKaoPayReadyResponseDto> kakaoPayRequest(@RequestHeader
            ("Authorization") String jwt, @RequestBody PaymentReadyVo paymentStockInfoVo) {

        String uuid = decodingToken.getUuid(jwt);
        KaKaoPayReadyResponseDto kaKaoPayReadyResponseDto =
                paymentServiceImp.KakaopayRequest(paymentStockInfoVo, uuid);

        return new BaseResponse<>(kaKaoPayReadyResponseDto);
    }

    @PostMapping("/kakaopay/approve")
    public BaseResponse<KaKaoPayApproveResponseDto> kakaoPayApprove(@RequestHeader
            ("Authorization") String jwt, @RequestBody PaymentApproveVo paymentApproveVo) {

        String uuid = decodingToken.getUuid(jwt);
        KaKaoPayApproveResponseDto kaKaoPayApproveResponseDto =
                paymentServiceImp.kakaoPayApprove(paymentApproveVo, uuid);

        return  new BaseResponse<>(kaKaoPayApproveResponseDto);

    }

    @GetMapping("/log/info")
    public BaseResponse<List<PaymentLogResponseDto>> paymentlog(@RequestHeader("Authorization") String jwt){


        String uuid = decodingToken.getUuid(jwt);
        List<PaymentLogResponseDto> paymentLogResponseDto = paymentServiceImp.paymentlog(uuid);

        return new BaseResponse<>(paymentLogResponseDto);
    }



}
