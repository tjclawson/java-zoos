package com.tjclawson.javazoos.repositories;

import com.tjclawson.javazoos.models.Animal;
import org.springframework.data.repository.CrudRepository;

public interface AnimalRepo extends CrudRepository<Animal, Long> {
}
