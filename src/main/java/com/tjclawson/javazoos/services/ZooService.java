package com.tjclawson.javazoos.services;

import com.tjclawson.javazoos.models.Zoo;

import java.util.List;

public interface ZooService {

    List<Zoo> findAll();

    List<Zoo> findByNameContaining(String zooname);

    Zoo findZooById(long id);

    void delete(long id);

    Zoo save(Zoo zoo);

    Zoo update(Zoo zoo, long id);

    void deleteZooAnimal(long zooid, long animalid);

    void addZooAnimal(long zooid, long animalid);
}
