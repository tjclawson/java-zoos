package com.tjclawson.javazoos.repositories;

import com.tjclawson.javazoos.models.Zoo;
import com.tjclawson.javazoos.views.ZooCountTelephones;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZooRepo extends CrudRepository<Zoo, Long> {

    List<Zoo> findByZoonameContaining(String zooname);

    Zoo findByZooname(String zooname);

    //gets count of telephones for each zoo
    //SELECT z.zooname as zoonamerpt, count(t.phoneid) as countphone FROM zoos z JOIN telephones t ON z.zooid = t.zooid GROUP BY z.zooname
    @Query(value = "SELECT z.zooname as zoonamerpt, count(t.phoneid) as countphone FROM zoos z JOIN telephones t ON z.zooid = t.zooid GROUP BY z.zooname",
            nativeQuery = true)
    List<ZooCountTelephones> getCountZooanimals();
}
