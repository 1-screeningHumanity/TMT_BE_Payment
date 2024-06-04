package com.TMT.TMT_BE_PaymentServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class TmtBePaymentServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmtBePaymentServerApplication.class, args);
	}

}
