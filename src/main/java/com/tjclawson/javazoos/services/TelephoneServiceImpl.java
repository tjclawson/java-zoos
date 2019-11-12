package com.tjclawson.javazoos.services;

import com.tjclawson.javazoos.exceptions.ResourceNotFoundException;
import com.tjclawson.javazoos.logging.Loggable;
import com.tjclawson.javazoos.models.Telephone;
import com.tjclawson.javazoos.repositories.TelephoneRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Service
@Qualifier("telephoneService")
public class TelephoneServiceImpl implements TelephoneService {

    private TelephoneRepo telephoneRepo;

    public TelephoneServiceImpl(TelephoneRepo telephoneRepo) {
        this.telephoneRepo = telephoneRepo;
    }

    @Override
    public List<Telephone> findAll() {
        List<Telephone> list = new ArrayList<>();
        telephoneRepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Telephone findTelephoneById(long id) {
        return telephoneRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Telephone with id " + id + " does not exist"));
    }

    @Override
    public List<Telephone> findByZooId(long id) {
        return telephoneRepo.findAllByZoo_Zooid(id);
    }

    @Override
    public void delete(long id) {
        if (telephoneRepo.findById(id).isPresent()) {
            telephoneRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Telephone with id " + id + " does not exist");
        }
    }

    @Override
    public Telephone update(long phoneid, String phonenumber) {
        if (telephoneRepo.findById(phoneid).isPresent()) {
            Telephone telephone = findTelephoneById(phoneid);
            telephone.setPhonenumber(phonenumber);
            return telephoneRepo.save(telephone);
        } else {
            throw new ResourceNotFoundException("Telephone with id " + phoneid + " does not exist");
        }
    }
}
