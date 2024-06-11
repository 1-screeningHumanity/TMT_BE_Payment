package com.TMT.TMT_BE_PaymentServer.wallet.infrastructure;

import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonQueryDslDto;
import org.springframework.transaction.annotation.Transactional;

public interface WalletQueryDslRepository {


    @Transactional
    void updateWon(ChargeWonQueryDslDto chargeWonQueryDslDto);
}