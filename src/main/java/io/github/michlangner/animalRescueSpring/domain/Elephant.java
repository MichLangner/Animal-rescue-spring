package io.github.michlangner.animalRescueSpring.domain;

import lombok.Value;

@Value
public class Elephant implements Animal {

    String id;
    String name;
    Integer age;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Integer getAge() {
        return null;
    }

    @Override
    public Specie getSpecie() {
        return Specie.ELEPHANT;
    }
}
