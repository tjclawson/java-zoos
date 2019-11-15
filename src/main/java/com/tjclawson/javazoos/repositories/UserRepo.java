package com.tjclawson.javazoos.repositories;

import com.tjclawson.javazoos.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {

    User findByUsername(String username);

    List<User> findByUsernameContainingIgnoreCase(String name);
}
