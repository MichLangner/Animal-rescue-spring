package io.github.michlangner.animalRescueSpring.api.dto;

import io.github.michlangner.animalRescueSpring.domain.Specie;
import lombok.Value;

@Value
public class SingleAnimalResponse {

    Integer age;
    Specie specie;
}
