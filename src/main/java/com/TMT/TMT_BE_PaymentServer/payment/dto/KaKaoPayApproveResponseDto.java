package com.TMT.TMT_BE_PaymentServer.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KaKaoPayApproveResponseDto {

    private String partner_order_id;
    private String partner_user_id;
    private String payment_method_type;
    private String item_name;
    private int quantity;
    private Amount amount;
}
