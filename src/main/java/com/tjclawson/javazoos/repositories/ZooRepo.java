package com.tjclawson.javazoos.repositories;

import com.tjclawson.javazoos.models.Zoo;
import org.springframework.data.repository.CrudRepository;

public interface ZooRepo extends CrudRepository<Zoo, Long> {
}
