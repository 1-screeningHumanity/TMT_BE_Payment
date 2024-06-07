package com.TMT.TMT_BE_PaymentServer.kafka;

import com.TMT.TMT_BE_PaymentServer.global.common.exception.CustomException;
import com.TMT.TMT_BE_PaymentServer.global.common.response.BaseResponseCode;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

@Component
public class PaymentKafkaProducer {
    private static final String TopicName = "kakaopay-event"; // 토픽 이름
    private static final String BootstrapServers = "localhost:9092";
    private static final int PARTITION_NUMBER = 0; // 파티션 번호

    public void sendMessage(String uuid, int cash) {

        Properties config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(config);

        // 메시지를 보낼 때 정수를 문자열로 변환
        String cashAsString = String.valueOf(cash);

        // Record 생성
        ProducerRecord<String, String> record = new ProducerRecord<>(TopicName, PARTITION_NUMBER,
                uuid, cashAsString); //Key = UUID, Value = cash
        try{
            producer.send(record); //레코드 전송
            producer.close();
        }catch (Exception e){
            throw new CustomException(BaseResponseCode.FAIL_SEND_MESSAGE);
        }

    }


}
