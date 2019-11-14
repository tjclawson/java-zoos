package com.tjclawson.javazoos.services;

import com.tjclawson.javazoos.JavazoosApplication;
import com.tjclawson.javazoos.models.Animal;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavazoosApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnimalServiceImplTest {

    @Autowired
    private AnimalService animalService;

    @Before
    public void AsetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void BtearDown() throws Exception {
    }

    @Test
    public void CfindAll() {
        assertEquals(7, animalService.findAll().size());
    }

    @Test
    public void DfindAnimalById() {
        assertEquals("lion", animalService.findAnimalById(1).getAnimaltype());
    }

    @Test
    public void Esave() {
        Animal newAnimal = new Animal("Quokka");
        Animal newAnimalWithId = animalService.save(newAnimal);
        assertEquals("Quokka", animalService.findAnimalById(newAnimalWithId.getAnimalid()).getAnimaltype());
    }

    @Test
    public void Fupdate() {
        Animal newAnimal = new Animal("Tortoise");
        Animal newAnimalWithId = animalService.save(newAnimal);
        newAnimalWithId.setAnimaltype("Turtle");
        animalService.update(newAnimalWithId.getAnimalid(), newAnimalWithId);
        assertEquals("Turtle", animalService.findAnimalById(newAnimalWithId.getAnimalid()).getAnimaltype());
    }

    @Test
    public void GgetCountAnimalZoos() {
        assertEquals(3, animalService.getCountAnimalZoos().get(0).getCountanimal());
    }

    @Test
    public void EZdelete() {
        animalService.delete(1);
        assertEquals(6, animalService.findAll().size());
    }
}