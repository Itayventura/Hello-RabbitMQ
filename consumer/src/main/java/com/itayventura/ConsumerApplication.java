package com.itayventura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConsumerApplication.class, args);
		Worker.consume();
	}


}
