package com.TMT.TMT_BE_PaymentServer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
public class TmtBePaymentServerApplication {

	public class paymentProducer{
		private KafkaProducer<String, String> producer;
		private final String topic = "payment-completed";

		public paymentProducer(){
			Properties props = new Properties();
			props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

			producer = new KafkaProducer<>(props);

		}
		public void sendMessage(String uuid, String cache) {
			String message = String.format("{\"uuid\":\"%s\", \"cache\":\"%s\"}", uuid, cache);
			producer.send(new ProducerRecord<>(topic, uuid, message));
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(TmtBePaymentServerApplication.class, args);
	}
}
