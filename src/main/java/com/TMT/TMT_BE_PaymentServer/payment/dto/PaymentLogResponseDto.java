package com.TMT.TMT_BE_PaymentServer.payment.dto;

import com.TMT.TMT_BE_PaymentServer.global.common.enumclass.PayName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentLogResponseDto {

    private PayName payName;
    private int totalAmount;
    private String itemName;
    private LocalDateTime createdAt;
    private String orderNum;

}
