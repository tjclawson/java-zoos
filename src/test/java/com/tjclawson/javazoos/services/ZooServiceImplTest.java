package com.tjclawson.javazoos.services;

import com.tjclawson.javazoos.JavazoosApplication;
import com.tjclawson.javazoos.models.Telephone;
import com.tjclawson.javazoos.models.Zoo;
import com.tjclawson.javazoos.models.ZooAnimals;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavazoosApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZooServiceImplTest {

    @Autowired
    private ZooService zooService;

    @Before
    public void AsetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void BtearDown() throws Exception {
    }

    @Test
    public void CfindAll() {
        assertEquals(5, zooService.findAll().size());
    }

    public void CfindAll(Pageable pageable) {
        assertEquals(0, zooService.findAll(pageable).size());
    }

    @Test
    public void DfindByNameContaining() {
        assertEquals("Gladys Porter Zoo", zooService.findByNameContaining("Gladys").get(0).getZooname());
    }

    @Test
    public void EfindZooById() {
        assertEquals("Point Defiance Zoo", zooService.findZooById(2).getZooname());
    }

    @Test
    public void Fdelete() {
        zooService.delete(2);
        assertEquals(4, zooService.findAll().size());
    }

    @Test
    public void Gsave() {
        ArrayList<ZooAnimals> zooAnimals = new ArrayList<>();
        Zoo myZoo = new Zoo("Too Many Zoos", zooAnimals);
        Zoo addZoo = zooService.save(myZoo);

        assertNotNull(addZoo);

        Zoo foundZoo = zooService.findZooById(addZoo.getZooid());
        assertEquals(addZoo.getZooname(), foundZoo.getZooname());
    }

    @Test
    public void Hupdate() {
        ArrayList<ZooAnimals> zooAnimals = new ArrayList<>();
        Zoo myZoo = new Zoo("Unchanged", zooAnimals);
        Zoo addZoo = zooService.save(myZoo);

        zooService.update(new Zoo("Changed", zooAnimals), addZoo.getZooid());
        assertEquals("Changed", zooService.findZooById(addZoo.getZooid()).getZooname());
    }

    @Test
    public void IdeleteZooAnimal() {
        zooService.deleteZooAnimal(3, 1);
        assertEquals(1, zooService.findZooById(3).getZooanimals().size());
    }

    @Test
    public void JaddZooAnimal() {
        zooService.addZooAnimal(1, 3);
        assertEquals(3, zooService.findZooById(1).getZooanimals().size());
    }

    @Test
    public void KgetCountZooTelephones() {
        assertEquals(3, zooService.getCountZooTelephones().get(0).getCountphone());
    }
}