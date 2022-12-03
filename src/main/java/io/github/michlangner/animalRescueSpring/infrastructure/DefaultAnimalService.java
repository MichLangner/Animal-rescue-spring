package io.github.michlangner.animalRescueSpring.infrastructure;

import io.github.michlangner.animalRescueSpring.domain.*;
import io.github.michlangner.animalRescueSpring.repositories.AnimalsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DefaultAnimalService implements AnimalService {

    private final AnimalsDao animalsDao;

    public DefaultAnimalService(AnimalsDao animalRepository) {
        this.animalsDao = animalRepository;
    }

    @Override
    public List<Animal> listOfAnimals(Integer limit) {
        return animalsDao.findAllAnimals(limit);
    }

    @Override
    public Page<Animal> listOfAnimals(Pageable pageable) {
        return animalsDao.findAnimals(pageable);
    }


    @Override
    public Animal singleAnimal(String id) {
        return animalsDao.findAnimal(id);
    }

    @Override
    public Animal createAnimal(Specie specie, Integer age, String name, String id) {
        Animal animal;
        String animalId;

        if (id == null) {
            animalId = UUID.randomUUID().toString();

        } else {
            animalId = id;
        }
        // losowy generator, bardzo mała kolizyjność

        switch (specie) {
            case DOG -> {
                animal = new Dog(animalId, name, age);
            }
            case CAT -> {
                animal = new Cat(animalId, name, age);
            }
            case ELEPHANT -> {
                animal = new Elephant(animalId, name, age);
            }
            default -> {
                throw new IllegalStateException("Unsupported specie ");
            }

        }

        animalsDao.saveAnimal(animal);
        return animal;
    }

    @Override
    public boolean deleteAnimal(String id) {
        animalsDao.deleteAnimal(id);
        log.info("Deleting animal by ID {}", id);
        return true;
    }

    @Override
    public boolean animalExist(String id) {
        return animalsDao.findAnimal(id) != null;
    }
}
