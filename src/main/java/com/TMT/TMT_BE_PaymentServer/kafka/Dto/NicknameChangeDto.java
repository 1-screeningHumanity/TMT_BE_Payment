package com.TMT.TMT_BE_PaymentServer.kafka.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NicknameChangeDto {

    private String beforeNickName;
    private String afterNickName;

}
