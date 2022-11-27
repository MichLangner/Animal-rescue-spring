package io.github.michlangner.animalRescueSpring.api.dto;

import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Value
public class ErrorDto {

    String message;
    // Instant w kazdym miejscu na swiecie zwroci nam ten sam czas (+0)
    Instant timestamp;
    HttpStatus httpStatusCode;
}
