package com.TMT.TMT_BE_PaymentServer.kafka.application;

import com.TMT.TMT_BE_PaymentServer.kafka.Dto.CreateWalletDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionCashDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.IncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.NicknameChangeDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.ReservationIncreaseWonDto;
import com.fasterxml.jackson.core.type.TypeReference;
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

    @KafkaListener(topics = "member-payment-signup")
    public void processMessage(String kafkaMessage){

        log.info("kafka Message : {}", kafkaMessage);

        CreateWalletDto createWalletDto = parseMessage(kafkaMessage,
                new TypeReference<CreateWalletDto>() {});
        if (createWalletDto != null) {
            log.info("createWalletDto uuid = {}", createWalletDto.getUuid());
            log.info("createWalletDto nickname = {}", createWalletDto.getNickname());
        }

        walletService.createWallet(createWalletDto);

    }

    //TradeServer -> Wallet Won 감소
    @KafkaListener(topics = "trade-payment-buy")
    public void deductionWon(String kafkaMessage) {

        log.info("kafka Message : {}", kafkaMessage);

        DeductionWonDto deductionWonDto = parseMessage(kafkaMessage,
                new TypeReference<DeductionWonDto>() {});

        if(deductionWonDto != null){
            log.info("deductionWonDto uuid = {}", deductionWonDto.getUuid());
            log.info("deductionWonDto won = {}", deductionWonDto.getPrice());
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
            log.info("increaseWonDto won = {}", increaseWonDto.getPrice());
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
            log.info("reservationIncreaseWon won = {}", reservationIncreaseWon.getPrice());
        }

        walletService.reservationIncreaseWon(reservationIncreaseWon);
    }

    @KafkaListener(topics = "member-subscribe-changenickname")
    public void changeNicknmae(String kafkaMessage){

        log.info("kafka Message : {}", kafkaMessage);
        NicknameChangeDto nicknameChangeDto = parseMessage(kafkaMessage,
                new TypeReference<NicknameChangeDto>() {});

        if(nicknameChangeDto != null){
            log.info("nicknameChangeDto beforenickname = {}", nicknameChangeDto.getBeforeNickName());
            log.info("nicknameChangeDto afternickname = {}", nicknameChangeDto.getAfterNickName());
        }

        walletService.changeNickname(nicknameChangeDto);
    }

    @KafkaListener(topics = "subscribe-payment-changecash")
    public void deductionCash(String kafkaMessage){

        log.info("kafka Message : {}", kafkaMessage);
        DeductionCashDto deductionCashDto = parseMessage(kafkaMessage,
                new TypeReference<DeductionCashDto>() {});

        if(deductionCashDto != null){
            log.info("deductionCashDto uuid = {}", deductionCashDto.getUuid());
            log.info("deductionCashDto cash = {}", deductionCashDto.getCash());
        }

        walletService.deductionCash(deductionCashDto);
    }

}
