package com.TMT.TMT_BE_PaymentServer.payment.infrastructure;

import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.querydsl.core.Tuple;
import java.util.List;

public interface PaymentLogQueryDslRepository {

    List<Tuple> paymentloginfo(String uuid);
}
