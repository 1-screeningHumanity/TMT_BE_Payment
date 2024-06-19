package com.TMT.TMT_BE_PaymentServer.wallet.application;

import com.TMT.TMT_BE_PaymentServer.kafka.Dto.CreateWalletDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.IncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.NicknameChangeDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.ReservationIncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.CashUpdateDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.CashDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonResponseDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.SendWalletInfoDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.WonInfoRequestDto;
import com.TMT.TMT_BE_PaymentServer.wallet.vo.ChargeWonRequestVo;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface WalletService {

    void createWallet(CreateWalletDto createWalletDto);

    void increaseCash(CashUpdateDto cashUpdateDto);

    //캐시조회
    CashDto hascash(String uuid);

    ChargeWonResponseDto chargewon(String uuid, ChargeWonRequestVo chargeWonRequestVo);

    void decreaseWon(DeductionWonDto deductionWonDto);

    void increaseWon(IncreaseWonDto increaseWonDto);

    void reservationIncreaseWon(ReservationIncreaseWonDto reservationIncreaseWon);
    //지갑 정보전송
    List<SendWalletInfoDto> sendWalletInfo();

    WonInfoRequestDto getWonInfo(String uuid);

    void changeNickname(NicknameChangeDto nicknameChangeDto);
}
