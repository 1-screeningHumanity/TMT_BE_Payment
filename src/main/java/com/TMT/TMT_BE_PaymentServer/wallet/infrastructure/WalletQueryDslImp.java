package com.TMT.TMT_BE_PaymentServer.wallet.infrastructure;

import static com.TMT.TMT_BE_PaymentServer.wallet.domain.QWallet.wallet;

import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.IncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.ReservationIncreaseWon;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonQueryDslDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class WalletQueryDslImp implements  WalletQueryDslRepository{

    private final JPAQueryFactory jpaQueryFactory;

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

    @Transactional
    @Override
    public void decreaseWon(DeductionWonDto deductionWonDto) {

        jpaQueryFactory
                .update(wallet)
                .set(wallet.won, wallet.won.subtract(deductionWonDto.getWon()))
                .where(wallet.uuid.eq(deductionWonDto.getUuid()))
                .execute();

    }

    @Transactional
    @Override
    public void increaseWon(IncreaseWonDto increaseWonDto){

        jpaQueryFactory
                .update(wallet)
                .set(wallet.won, wallet.won.add(increaseWonDto.getWon()))
                .where(wallet.uuid.eq(increaseWonDto.getUuid()))
                .execute();
    }

    @Transactional
    @Override
    public void reservationIncreaseWon(ReservationIncreaseWon reservationIncreaseWon){

        jpaQueryFactory
                .update(wallet)
                .set(wallet.won, wallet.won.add(reservationIncreaseWon.getWon()))
                .where(wallet.uuid.eq(reservationIncreaseWon.getUuid()))
                .execute();

    }

}