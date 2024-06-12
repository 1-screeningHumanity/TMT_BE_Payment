package com.TMT.TMT_BE_PaymentServer.kafka.application;

import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.IncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.ReservationIncreaseWonDto;
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
    private final ObjectMapper mapper = new ObjectMapper();

    //Json Parsing 처리 메소드
    private <T> T parseMessage(String kafkaMessage, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(kafkaMessage, typeReference);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse Kafka message", e);
            return null;
        }
    }

    //Member Server -> Wallet 생성

    @KafkaListener(topics = "member-payment-singup")
    public void processMessage(String kafkaMessage){

        log.info("kafka Message : {}", kafkaMessage);
        WalletDto walletDto = parseMessage(kafkaMessage,
                new TypeReference<WalletDto>() {});
        if (walletDto != null){
            log.info("findDto Id ={}",walletDto.getUuid());
        }
        walletService.createWallet(walletDto);
    }

    //TradeServer -> Wallet Won 감소
    @KafkaListener(topics = "trade-payment-buy")
    public void deductionWon(String kafkaMessage) {

        log.info("kafka Message : {}", kafkaMessage);

        DeductionWonDto deductionWonDto = parseMessage(kafkaMessage,
                new TypeReference<DeductionWonDto>() {});

        if(deductionWonDto != null){
            log.info("deductionWonDto uuid = {}", deductionWonDto.getUuid());
            log.info("deductionWonDto won = {}", deductionWonDto.getWon());
        }

        walletService.decreaseWon(deductionWonDto);
    }

    //Trade Server 매수 -> Wallet
    @KafkaListener(topics = "trade-payment-sale")
    public void increaseWon(String kafkaMessage) {
        log.info("Received Kafka message: {}", kafkaMessage);

        IncreaseWonDto increaseWonDto = parseMessage(kafkaMessage,
                new TypeReference<IncreaseWonDto>() {});

        if (increaseWonDto != null) {
            log.info("increaseWonDto uuid = {}", increaseWonDto.getUuid());
            log.info("increaseWonDto won = {}", increaseWonDto.getWon());
            walletService.increaseWon(increaseWonDto);
        }
    }

    //TradeServer -> Wallet Won증가(예약매수 취소)
    @KafkaListener(topics = "trade-payment-reservationcancel")
    public void reservationIncreaseWon(String kafkaMessage) {

        log.info("kafka Message : {}", kafkaMessage);
        ReservationIncreaseWonDto reservationIncreaseWon = parseMessage(kafkaMessage,
                new TypeReference<ReservationIncreaseWonDto>() {});

        if(reservationIncreaseWon != null){
            log.info("reservationIncreaseWon uuid = {}", reservationIncreaseWon.getUuid());
            log.info("reservationIncreaseWon won = {}", reservationIncreaseWon.getWon());
        }

        walletService.reservationIncreaseWon(reservationIncreaseWon);
    }

}
