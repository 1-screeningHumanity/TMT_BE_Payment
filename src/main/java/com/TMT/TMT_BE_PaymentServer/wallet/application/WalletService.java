package com.TMT.TMT_BE_PaymentServer.wallet.application;

import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.IncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.ReservationIncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.WalletDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.CashUpdateDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.CashDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonResponseDto;
import com.TMT.TMT_BE_PaymentServer.wallet.vo.ChargeWonRequestVo;
import org.springframework.transaction.annotation.Transactional;

public interface WalletService {

    @Transactional
    void createWallet(String uuid);

    @Transactional
    void updateWallet(CashUpdateDto cashUpdateDto);

    //캐시조회
    CashDto hascash(String uuid);

    ChargeWonResponseDto chargewon(String uuid, ChargeWonRequestVo chargeWonRequestVo);

    void decreaseWon(DeductionWonDto deductionWonDto);

    void increaseWon(IncreaseWonDto increaseWonDto);

    void reservationIncreaseWon(ReservationIncreaseWonDto reservationIncreaseWon);
}
