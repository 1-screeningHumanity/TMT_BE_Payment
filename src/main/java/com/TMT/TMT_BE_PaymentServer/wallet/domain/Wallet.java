package com.TMT.TMT_BE_PaymentServer.wallet.domain;


import com.TMT.TMT_BE_PaymentServer.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "wallet")
@Entity
public class Wallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wallet_id;

    private String uuid; //uuid

    private int cash; //캐시

    private int won; //원
    @Builder
    public Wallet(Long wallet_id, String uuid, int cash, int won) {
        this.wallet_id = wallet_id;
        this.uuid = uuid;
        this.cash = cash;
        this.won = won;
    }

}
