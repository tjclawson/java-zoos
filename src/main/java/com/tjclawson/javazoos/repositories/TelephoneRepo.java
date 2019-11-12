package com.tjclawson.javazoos.repositories;

import com.tjclawson.javazoos.models.Telephone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TelephoneRepo extends CrudRepository<Telephone, Long> {

    List<Telephone> findAllByZoo_Zooid(long id);
}
