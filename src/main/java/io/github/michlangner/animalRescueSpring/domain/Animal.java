package io.github.michlangner.animalRescueSpring.domain;

public interface Animal {

    public String getId();
    public String getName();
    public Integer getAge();
    public Specie getSpecie();
}
