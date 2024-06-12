package com.TMT.TMT_BE_PaymentServer.wallet.application;

import com.TMT.TMT_BE_PaymentServer.kafka.Dto.WalletDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.CashUpdateDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.CashDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonResponseDto;
import com.TMT.TMT_BE_PaymentServer.wallet.vo.ChargeWonRequestVo;
import org.springframework.transaction.annotation.Transactional;

public interface WalletService {
    void createWallet(WalletDto walletDto);

    @Transactional
    void updateWallet(CashUpdateDto cashUpdateDto);

    //캐시조회
    CashDto hascash(String uuid);

    ChargeWonResponseDto chargewon(String uuid, ChargeWonRequestVo chargeWonRequestVo);
}
