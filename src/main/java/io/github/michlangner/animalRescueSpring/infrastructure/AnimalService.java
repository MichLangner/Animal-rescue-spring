package io.github.michlangner.animalRescueSpring.infrastructure;

import io.github.michlangner.animalRescueSpring.domain.Animal;
import io.github.michlangner.animalRescueSpring.domain.Specie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnimalService {


    List<Animal> listOfAnimals(Integer limit);
    Page<Animal> listOfAnimals( Pageable pageable);

    Animal singleAnimal(String id);
    Animal createAnimal(Specie specie, Integer age, String name , String id);
    boolean deleteAnimal(String id);
    boolean animalExist(String id);
}
