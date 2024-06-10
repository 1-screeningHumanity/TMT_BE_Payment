package com.TMT.TMT_BE_PaymentServer.kafka.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.WalletDto;
import com.TMT.TMT_BE_PaymentServer.wallet.application.WalletServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaConsumerService {

    private final WalletServiceImp walletService;

    @KafkaListener(topics = "member-payment-signup")
    public void processMessage(String kafkaMessage){

        log.info("kafka Message : {}", kafkaMessage);

        WalletDto walletDto = new WalletDto();
        ObjectMapper mapper = new ObjectMapper();
        try{
            walletDto = mapper.readValue(kafkaMessage, new TypeReference<>() {});
            log.info("findDto Id ={}",walletDto.getUuid());
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        walletService.createWallet(walletDto);
    }

}
