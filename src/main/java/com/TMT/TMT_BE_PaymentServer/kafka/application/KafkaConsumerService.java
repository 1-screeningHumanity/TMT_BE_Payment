package com.TMT.TMT_BE_PaymentServer.kafka.application;

import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.IncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.ReservationIncreaseWon;
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

    @KafkaListener(topics = "trade-payment-buy")
    public void deductionWon(String kafkaMessage) {

        log.info("kafka Message : {}", kafkaMessage);

        DeductionWonDto deductionWonDto = new DeductionWonDto();
        ObjectMapper mapper = new ObjectMapper();
        try{
            deductionWonDto = mapper.readValue(kafkaMessage,
                    new TypeReference<DeductionWonDto>() {});
            log.info("deductionWonDto Id ={}",deductionWonDto.getUuid());
            log.info("deductionWonDto won ={}",deductionWonDto.getWon());
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        walletService.decreaseWon(deductionWonDto);
    }

    @KafkaListener(topics = "trade-payment-sale")
    public void increaseWon(String kafkaMessage) {

        log.info("kafka Message : {}", kafkaMessage);

        IncreaseWonDto increaseWonDto = new IncreaseWonDto();
        ObjectMapper mapper = new ObjectMapper();
        try{
            increaseWonDto = mapper.readValue(kafkaMessage,
                    new TypeReference<IncreaseWonDto>() {});
            log.info("IncreaseWonDto uuid ={}",increaseWonDto.getUuid());
            log.info("IncreaseWonDto won ={}",increaseWonDto.getWon());
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        walletService.increaseWon(increaseWonDto);
    }

    @KafkaListener(topics = "trade-payment-reservationcancel")
    public void reservationIncreaseWon(String kafkaMessage) {

        log.info("kafka Message : {}", kafkaMessage);

        ReservationIncreaseWon reservationIncreaseWon = new ReservationIncreaseWon();
        ObjectMapper mapper = new ObjectMapper();
        try{
            reservationIncreaseWon = mapper.readValue(kafkaMessage,
                    new TypeReference<ReservationIncreaseWon>() {});
            log.info("reservationIncreaseWon uuid ={}",reservationIncreaseWon.getUuid());
            log.info("reservationIncreaseWon won ={}",reservationIncreaseWon.getWon());
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        walletService.reservationIncreaseWon(reservationIncreaseWon);
    }



}
