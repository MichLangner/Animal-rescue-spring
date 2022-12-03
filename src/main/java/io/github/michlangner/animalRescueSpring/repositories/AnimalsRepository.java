package io.github.michlangner.animalRescueSpring.repositories;

import io.github.michlangner.animalRescueSpring.domain.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalsRepository extends MongoRepository<Animal,String> {


    public Animal findAnimalById(String id);
    public void deleteById(String id);
}
