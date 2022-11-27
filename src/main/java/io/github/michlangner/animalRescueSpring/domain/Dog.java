package io.github.michlangner.animalRescueSpring.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@EqualsAndHashCode
@ToString
@Value // value - dodaje konstruktor i robi pola prywatne
public class Dog implements Animal{

    String id;
    String name;
    Integer age;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return "Dog " + name;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public Specie getSpecie() {
        return Specie.DOG;
    }
}
