package io.github.michlangner.animalRescueSpring.api.dto;

import io.github.michlangner.animalRescueSpring.domain.Animal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true,access = AccessLevel.PRIVATE)
public class AllAnimalsResponse {

    List<Animal> animals;
    Integer totalAnimals;

    public AllAnimalsResponse(List<Animal> animals){
        this.animals = animals;
        this.totalAnimals = animals.size();
    }
}
