package com.sparta.icticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IcTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcTicketApplication.class, args);
	}

}
