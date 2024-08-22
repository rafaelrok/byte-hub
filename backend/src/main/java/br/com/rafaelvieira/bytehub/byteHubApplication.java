package br.com.rafaelvieira.bytehub;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class byteHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(byteHubApplication.class, args);
    }

}
