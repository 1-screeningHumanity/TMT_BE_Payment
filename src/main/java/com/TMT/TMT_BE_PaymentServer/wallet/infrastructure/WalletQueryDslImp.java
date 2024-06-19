package com.TMT.TMT_BE_PaymentServer.wallet.infrastructure;

import static com.TMT.TMT_BE_PaymentServer.wallet.domain.QWallet.wallet;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.IncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.ReservationIncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.CashUpdateDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonQueryDslDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class WalletQueryDslImp implements  WalletQueryDslRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public void increaseCash(CashUpdateDto cashUpdateDto){

        jpaQueryFactory
                .update(wallet)
                .set(wallet.cash, wallet.cash.add(cashUpdateDto.getCash()))
                .where(wallet.uuid.eq(cashUpdateDto.getUuid()))
                .execute();

    }

    @Override
    @Transactional
    public void updateWon(ChargeWonQueryDslDto chargeWonQueryDslDto){

        jpaQueryFactory
                .update(wallet)
                .set(wallet.won, wallet.won.add(chargeWonQueryDslDto.getWon()))
                .set(wallet.cash, wallet.cash.subtract(chargeWonQueryDslDto.getCash()))
                .where(wallet.uuid.eq(chargeWonQueryDslDto.getUuid()))
                .execute();

    }

    @Override
    public List<Tuple> sendwalletinfo() {
        return jpaQueryFactory
                .select(wallet.uuid, wallet.won)
                .from(wallet)
                .fetch();
    }
    @Transactional
    @Override
    public void decreaseWon(DeductionWonDto deductionWonDto) {

        jpaQueryFactory
                .update(wallet)
                .set(wallet.won, wallet.won.subtract(deductionWonDto.getPrice()))
                .where(wallet.uuid.eq(deductionWonDto.getUuid()))
                .execute();

    }

    @Transactional
    @Override
    public void increaseWon(IncreaseWonDto increaseWonDto){

        jpaQueryFactory
                .update(wallet)
                .set(wallet.won, wallet.won.add(increaseWonDto.getPrice()))
                .where(wallet.uuid.eq(increaseWonDto.getUuid()))
                .execute();
    }

    @Transactional
    @Override
    public void reservationIncreaseWon(ReservationIncreaseWonDto reservationIncreaseWon){

        jpaQueryFactory
                .update(wallet)
                .set(wallet.won, wallet.won.add(reservationIncreaseWon.getPrice()))
                .where(wallet.uuid.eq(reservationIncreaseWon.getUuid()))
                .execute();

    }


}