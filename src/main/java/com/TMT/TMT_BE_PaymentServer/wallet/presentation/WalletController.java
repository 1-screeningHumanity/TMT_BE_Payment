package com.TMT.TMT_BE_PaymentServer.wallet.presentation;


import com.TMT.TMT_BE_PaymentServer.global.common.exception.CustomException;
import com.TMT.TMT_BE_PaymentServer.global.common.response.BaseResponse;
import com.TMT.TMT_BE_PaymentServer.global.common.response.BaseResponseCode;
import com.TMT.TMT_BE_PaymentServer.global.common.token.DecodingToken;
import com.TMT.TMT_BE_PaymentServer.wallet.application.WalletServiceImp;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.CashDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonResponseDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.SendWalletInfoDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.WonInfoRequestDto;
import com.TMT.TMT_BE_PaymentServer.wallet.vo.ChargeWonRequestVo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final DecodingToken decodingToken;
    private final WalletServiceImp walletServiceImp;

    @GetMapping("/cash")
    public BaseResponse<CashDto> cashinfo(@RequestHeader ("Authorization") String jwt){

        String uuid = decodingToken.getUuid(jwt);
        CashDto cashDto = walletServiceImp.hascash(uuid);
        return new BaseResponse<>(cashDto);
    }

    @PostMapping("/charge/won")
    public BaseResponse<ChargeWonResponseDto> chargeWon(@RequestHeader("Authorization") String jwt,
            @RequestBody ChargeWonRequestVo chargeWonRequestVo){

        if (jwt == null){
            throw new CustomException(BaseResponseCode.WRONG_TOKEN);
        }

        String uuid = decodingToken.getUuid(jwt);

        ChargeWonResponseDto chargeWonResponseDto =
                walletServiceImp.chargewon(uuid, chargeWonRequestVo);

        return new BaseResponse<>(chargeWonResponseDto);
    }

    @GetMapping("/woninfo")
    public BaseResponse<WonInfoRequestDto> wonInfo(@RequestHeader ("Authorization") String jwt){

        String uuid = decodingToken.getUuid(jwt);
        WonInfoRequestDto wonInfoRequestDto = walletServiceImp.getWonInfo(uuid);

        return new BaseResponse<>(wonInfoRequestDto);
    }

    @GetMapping("/send/dailywalletinfo")
    public BaseResponse<List<SendWalletInfoDto>> sendWalletInfo(){

        List<SendWalletInfoDto> sendWalletInfo = walletServiceImp.sendWalletInfo();
        return new BaseResponse<>(sendWalletInfo);

    }


}