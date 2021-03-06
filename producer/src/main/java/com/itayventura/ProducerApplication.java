package com.itayventura;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class ProducerApplication {

	private final static String QUEUE_NAME = "hello";
	private final static Logger LOGGER = LoggerFactory.getLogger(ProducerApplication.class);


	public static void main(String[] args) throws Exception {
		SpringApplication.run(ProducerApplication.class, args);

		produce();
	}

	private static void produce() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
			 Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello World!";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
			LOGGER.info(" [x] Sent '" + message + "'");
		}
	}

}
