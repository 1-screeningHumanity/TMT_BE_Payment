package com.TMT.TMT_BE_PaymentServer.payment.infrastructure;

import static com.TMT.TMT_BE_PaymentServer.payment.domain.QPaymentLog.paymentLog;

import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentLogQueryDslImp implements PaymentLogQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Tuple> paymentloginfo (String uuid){

        return jpaQueryFactory.select(paymentLog.createdAt,paymentLog.orderNum,paymentLog.totalAmount
                ,paymentLog.itemName, paymentLog.payName)
                .from(paymentLog)
                .where(paymentLog.uuid.eq(uuid))
                .fetch();

    }




}
