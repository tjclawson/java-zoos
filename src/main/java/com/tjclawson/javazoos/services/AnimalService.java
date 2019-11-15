package com.tjclawson.javazoos.services;

import com.tjclawson.javazoos.models.Animal;
import com.tjclawson.javazoos.views.AnimalCountZoos;

import java.util.List;

public interface AnimalService {

    List<Animal> findAll();

    Animal findAnimalById(long id);

    void delete(long id);

    Animal save(Animal animal);

    Animal update(long id, Animal animal);

    List<AnimalCountZoos> getCountAnimalZoos();
}
