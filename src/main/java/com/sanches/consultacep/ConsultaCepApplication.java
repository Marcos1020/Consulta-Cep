package com.sanches.consultacep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ConsultaCepApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultaCepApplication.class, args);
	}

}
