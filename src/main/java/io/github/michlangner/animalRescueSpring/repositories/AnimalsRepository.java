package io.github.michlangner.animalRescueSpring.repositories;

import io.github.michlangner.animalRescueSpring.domain.Animal;

import java.util.List;

public interface AnimalsRepository {

    void saveAnimal(Animal animal);
    Animal findAnimal(String id);
    List<Animal> findAllAnimals(Integer limit);
    void deleteAnimal(String id);
}
