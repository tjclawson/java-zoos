package com.tjclawson.javazoos.services;

import com.tjclawson.javazoos.models.Telephone;

import java.util.List;

public interface TelephoneService {

    List<Telephone> findAll();

    Telephone findTelephoneById(long id);

    List<Telephone> findByZooId(long id);

    void delete(long id);

    Telephone update(long phoneid, String phonenumber);
}
