package com.TMT.TMT_BE_PaymentServer.wallet.application;

import com.TMT.TMT_BE_PaymentServer.kafka.Dto.WalletDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.CashUpdateDto;
import org.springframework.transaction.annotation.Transactional;

public interface WalletService {
    void createWallet(WalletDto walletDto);

    @Transactional
    void updateWallet(CashUpdateDto cashUpdateDto);
}
