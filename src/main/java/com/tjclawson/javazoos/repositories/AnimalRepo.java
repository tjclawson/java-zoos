package com.tjclawson.javazoos.repositories;

import com.tjclawson.javazoos.models.Animal;
import com.tjclawson.javazoos.views.AnimalCountZoos;
import com.tjclawson.javazoos.views.Count;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnimalRepo extends CrudRepository<Animal, Long> {

    @Query(value = "SELECT COUNT(*) AS count FROM zooanimals WHERE zooid = :zooid AND animalid = :animalid", nativeQuery = true)
    Count checkZooAnimalCombo(long zooid, long animalid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM zooanimals WHERE zooid = :zooid AND animalid = :animalid")
    void deleteZooAnimals(long zooid, long animalid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO zooanimals(zooid, animalid) VALUES (:zooid, :animalid)", nativeQuery = true)
    void insertZooanimal(long zooid, long animalid);

    // returns list of animals and counts
    // SELECT a.animalid as animalnamerpt, a.animaltype, count(z.animalid) as countanimal FROM animals a JOIN zooanimals z ON z.animalid = a.animalid GROUP BY a.animalid
    @Query(value = "SELECT a.animalid as animalnamerpt, a.animaltype, count(z.animalid) as countanimal FROM animals a JOIN zooanimals z ON z.animalid = a.animalid GROUP BY a.animalid")
    List<AnimalCountZoos> getListOfAnimalsZoos();

    @Query(value = "UPDATE animal SET animaltype = :animaltype WHERE animalid = :animalid")
    Animal updateAnimaltype(long animalid, String animaltype);


    // returns list of animals and zooid where they are
    //SELECT a.animalid as animalnamerpt, a.animaltype, z.zooid FROM animals a JOIN zooanimals z ON z.animalid = a.animalid
}
