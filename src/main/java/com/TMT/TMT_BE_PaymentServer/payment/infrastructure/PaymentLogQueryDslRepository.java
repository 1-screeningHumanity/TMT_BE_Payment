package com.TMT.TMT_BE_PaymentServer.payment.infrastructure;

import com.querydsl.core.Tuple;
import java.util.List;

public interface PaymentLogQueryDslRepository {

    List<Tuple> paymentloginfo(String uuid);
}
