package com.TMT.TMT_BE_PaymentServer.wallet.infrastructure;

import static com.TMT.TMT_BE_PaymentServer.wallet.domain.QWallet.wallet;

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
    public void updateWon(ChargeWonQueryDslDto chargeWonQueryDslDto){

        jpaQueryFactory
                .update(wallet)
                .set(wallet.won, wallet.won.add(chargeWonQueryDslDto.getWon()))
                .set(wallet.cash, wallet.cash.subtract(chargeWonQueryDslDto.getCash()))
                .where(wallet.uuid.eq(chargeWonQueryDslDto.getUuid()))
                .execute();

    }

    @Override
    public List<Tuple> sendwalletinfo(){
        return  jpaQueryFactory
                 .select(wallet.uuid, wallet.won)
                 .from(wallet)
                .fetch();

    }

}