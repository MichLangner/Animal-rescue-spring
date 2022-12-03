package io.github.michlangner.animalRescueSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.lang.reflect.Proxy;

@SpringBootApplication
@EnableMongoRepositories
public class AnimalRescueSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalRescueSpringApplication.class, args);
    }

}
