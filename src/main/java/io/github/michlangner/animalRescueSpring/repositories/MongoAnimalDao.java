package io.github.michlangner.animalRescueSpring.repositories;

import io.github.michlangner.animalRescueSpring.domain.Animal;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary

public class MongoAnimalDao implements AnimalsDao {

    private final AnimalsRepository animalsRepository;

    public MongoAnimalDao(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
    }

    @Override
    public void saveAnimal(Animal animal) {
        animalsRepository.save(animal);

    }

    @Override
    public Animal findAnimal(String id) {
        return animalsRepository.findAnimalById(id);

    }

    @Override
    public List<Animal> findAllAnimals(Integer limit) {
       return animalsRepository.findAll();
    }

    @Override
    public void deleteAnimal(String id) {
        animalsRepository.deleteById(id);

    }

    @Override
    public Page<Animal> findAnimals(Pageable pageable) {
       return animalsRepository.findAll(pageable);

    }
}
