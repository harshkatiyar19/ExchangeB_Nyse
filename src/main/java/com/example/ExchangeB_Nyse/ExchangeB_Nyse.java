package com.example.ExchangeB_Nyse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExchangeB_Nyse {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeB_Nyse.class, args);
	}

}
