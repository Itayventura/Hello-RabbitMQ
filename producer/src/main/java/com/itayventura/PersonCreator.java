package com.itayventura;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PersonCreator implements CommandLineRunner {
    @Value("${amqp.queue.name}")
    private String queueName;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonCreator.class);

    private final RabbitTemplate rabbitTemplate;
    private final ConfigurableApplicationContext context;
    private final ObjectMapper objectMapper;

    @Autowired
    public PersonCreator(RabbitTemplate rabbitTemplate, ConfigurableApplicationContext context, ObjectMapper objectMapper){
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;
        this.objectMapper = objectMapper;
    }
    @Override
    public void run(String... args) {
        List<Person> persons = new ArrayList<>(
                Arrays.asList(
                        new Person("first", "person"),
                        new Person("second", "person"),
                        new Person("third", "person"),
                        new Person("fourth", "person"),
                        new Person("fifth", "person"),
                        new Person("sixth", "person"),
                        new Person("seventh", "person")
                ));
        persons.forEach(person->{
            try {
                String jsonString = objectMapper.writeValueAsString(person);
                rabbitTemplate.convertAndSend(queueName, jsonString);
                LOGGER.info(" [x] Sent " + jsonString);
            } catch (JsonProcessingException e) {
                LOGGER.error("Parsing Exception", e);
            }
        });
        System.exit(SpringApplication.exit(context));
    }
}
