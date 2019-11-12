package com.tjclawson.javazoos.services;

import com.tjclawson.javazoos.models.Animal;
import com.tjclawson.javazoos.repositories.AnimalRepo;
import com.tjclawson.javazoos.repositories.ZooRepo;
import com.tjclawson.javazoos.views.AnimalCountZoos;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier(value = "animalService")
public class AnimalServiceImpl implements AnimalService {

    private AnimalRepo animalRepo;
    private ZooRepo zooRepo;

    public AnimalServiceImpl(AnimalRepo animalRepo, ZooRepo zooRepo) {
        this.animalRepo = animalRepo;
        this.zooRepo = zooRepo;
    }

    @Override
    public List<Animal> findAll() {
        List<Animal> list = new ArrayList<>();
        animalRepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Animal findAnimalById(long id) {
        return animalRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("no sir"));
    }

    @Transactional
    @Override
    public void delete(long id) {
        animalRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("no sir"));
        animalRepo.deleteById(id);
    }

    @Override
    public Animal save(Animal animal) {
        Animal newAnimal = new Animal();
        newAnimal.setAnimaltype(animal.getAnimaltype());
        if (animal.getZooanimals().size() > 0) throw new EntityNotFoundException("ZooAnimals not created through animal");
        return animalRepo.save(animal);
    }

    @Override
    public Animal update(long id, Animal animal) {

        if (animal.getAnimaltype() == null) throw new EntityNotFoundException("No animal type to update");
        if (animal.getZooanimals().size() > 0) throw new EntityNotFoundException("Zooanimals not updated through animal");

        if (animalRepo.findById(id) != null) {
            animalRepo.updateAnimaltype(id, animal.getAnimaltype());
        } else throw new EntityNotFoundException("No animal with id " + id + " exists");
        return findAnimalById(id);
    }

    @Override
    public List<AnimalCountZoos> getCountAnimalZoos() {
        return animalRepo.getListOfAnimalsZoos();
    }
}
