package com.tjclawson.javazoos.repositories;

import com.tjclawson.javazoos.models.Telephone;
import org.springframework.data.repository.CrudRepository;

public interface TelephoneRepo extends CrudRepository<Telephone, Long> {
}
