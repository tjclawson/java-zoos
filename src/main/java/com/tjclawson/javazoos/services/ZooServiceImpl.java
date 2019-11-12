package com.tjclawson.javazoos.services;

import com.tjclawson.javazoos.models.Telephone;
import com.tjclawson.javazoos.models.Zoo;
import com.tjclawson.javazoos.repositories.AnimalRepo;
import com.tjclawson.javazoos.repositories.ZooRepo;
import com.tjclawson.javazoos.views.ZooCountTelephones;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("zooService")
public class ZooServiceImpl implements ZooService {

    private ZooRepo zooRepo;
    private AnimalRepo animalRepo;

    public ZooServiceImpl(ZooRepo zooRepo, AnimalRepo animalRepo) {
        this.zooRepo = zooRepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public List<Zoo> findAll() {

        List<Zoo> list = new ArrayList<>();
        zooRepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Zoo> findByNameContaining(String zooname) {
        return zooRepo.findByZoonameContaining(zooname);
    }

    @Override
    public Zoo findZooById(long id) {
        return zooRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("not today"));
    }

    @Transactional
    @Override
    public void delete(long id) {
        zooRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("stop it"));
        zooRepo.deleteById(id);
    }

    @Transactional
    @Override
    public Zoo save(Zoo zoo) {
        if (zooRepo.findByZooname(zoo.getZooname()) != null) {
            throw new EntityNotFoundException(zoo.getZooname() + " already exists");
        }
        Zoo newZoo = new Zoo();
        newZoo.setZooname(zoo.getZooname());

        for( Telephone t : zoo.getTelephones()) {
            newZoo.getTelephones().add(new Telephone(t.getPhonetype(), t.getPhonenumber(), newZoo));
        }

        return zooRepo.save(newZoo);
    }

    @Transactional
    @Override
    public Zoo update(Zoo zoo, long id) {
        Zoo newZoo = findZooById(id);
        if (zoo.getZooname() != null) {
            newZoo.setZooname(zoo.getZooname());
        }

        if (zoo.getTelephones() != null) {
            for (Telephone t : zoo.getTelephones()) {
                newZoo.getTelephones().add(new Telephone(t.getPhonetype(), t.getPhonenumber(), newZoo));
            }
        }

        return zooRepo.save(newZoo);
    }

    @Transactional
    @Override
    public void deleteZooAnimal(long zooid, long animalid) {
        zooRepo.findById(zooid).orElseThrow(() -> new EntityNotFoundException("Zoo id " + zooid + " not found"));
        animalRepo.findById(zooid).orElseThrow(() -> new EntityNotFoundException("Zoo id " + animalid + " not found"));

        if (animalRepo.checkZooAnimalCombo(zooid, animalid).getCount() > 0) {
            animalRepo.deleteZooAnimals(zooid, animalid);
        } else throw new EntityNotFoundException("Zoo Animal Combo does not exist");

    }

    @Override
    public void addZooAnimal(long zooid, long animalid) {
        zooRepo.findById(zooid).orElseThrow(() -> new EntityNotFoundException("Zoo id " + zooid + " not found"));
        animalRepo.findById(zooid).orElseThrow(() -> new EntityNotFoundException("Zoo id " + animalid + " not found"));

        if (animalRepo.checkZooAnimalCombo(zooid, animalid).getCount() <= 0) {
            animalRepo.insertZooanimal(zooid, animalid);
        } else throw new EntityNotFoundException("Zoo animal combo already exists");
    }

    @Override
    public List<ZooCountTelephones> getCountZooTelephones() {
        return zooRepo.getCountZooanimals();
    }
}
