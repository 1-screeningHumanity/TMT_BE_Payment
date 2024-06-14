package com.TMT.TMT_BE_PaymentServer.wallet.infrastructure;

import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonQueryDslDto;
import com.querydsl.core.Tuple;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface WalletQueryDslRepository {



    void updateWon(ChargeWonQueryDslDto chargeWonQueryDslDto);

    List<Tuple> sendwalletinfo();
}