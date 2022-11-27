package io.github.michlangner.animalRescueSpring.repositories;

import io.github.michlangner.animalRescueSpring.domain.Animal;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryAnimalRepository implements AnimalsRepository {

    private final Map<String,Animal> animals = new HashMap<>();
    @Override
    public void saveAnimal(Animal animal) {
        animals.put(animal.getId(),animal);

    }

    @Override
    public Animal findAnimal(String id) {
        return animals.get(id);
    }

    @Override
    public List<Animal> findAllAnimals(Integer limit) {
        return animals.values().stream().limit(limit.longValue()).toList();
    }

    @Override
    public void deleteAnimal(String id) {
animals.remove(id);
    }
}
