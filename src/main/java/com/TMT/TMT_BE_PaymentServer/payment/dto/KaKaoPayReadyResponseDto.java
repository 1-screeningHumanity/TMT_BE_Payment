package com.TMT.TMT_BE_PaymentServer.payment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KaKaoPayReadyResponseDto {

    private String tid;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url;
    private String partner_order_id;




    public void getPartner_order_id(String orderNum) {
        this.partner_order_id = orderNum;
    }
}
