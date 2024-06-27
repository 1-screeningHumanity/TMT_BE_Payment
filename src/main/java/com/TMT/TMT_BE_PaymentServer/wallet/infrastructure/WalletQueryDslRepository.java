package com.TMT.TMT_BE_PaymentServer.wallet.infrastructure;

import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionCashDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.IncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.NicknameChangeDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.ReservationIncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.CashUpdateDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonQueryDslDto;
import com.querydsl.core.Tuple;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface WalletQueryDslRepository {

    void increaseCash(CashUpdateDto cashUpdateDto);

    void updateWon(ChargeWonQueryDslDto chargeWonQueryDslDto);

    List<Tuple> sendwalletinfo();

    void decreaseWon(DeductionWonDto deductionWonDto);

    void increaseWon(IncreaseWonDto increaseWonDto);

    void reservationIncreaseWon(ReservationIncreaseWonDto reservationIncreaseWon);

    void changeNickname(NicknameChangeDto nicknameChangeDto);

    void decreaseCash(DeductionCashDto deductionCashDto);
}