package com.tjclawson.javazoos.services;

import com.tjclawson.javazoos.JavazoosApplication;
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
public class TelephoneServiceImplTest {

    @Autowired
    private TelephoneService telephoneService;

    @Before
    public void AsetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void BtearDown() throws Exception {
    }

    @Test
    public void CfindAll() {
        assertEquals(5, telephoneService.findAll().size());
    }

    @Test
    public void DfindTelephoneById() {
        assertEquals("main", telephoneService.findTelephoneById(1).getPhonetype());
    }

    @Test
    public void EfindByZooId() {
        assertEquals(3, telephoneService.findByZooId(1).size());
    }

    @Test
    public void Gupdate() {
        telephoneService.update(1, "000-000-0000");
        assertEquals("000-000-0000", telephoneService.findTelephoneById(1).getPhonenumber());
    }

    @Test
    public void Hdelete() {
        telephoneService.delete(1);
        assertEquals(4, telephoneService.findAll().size());
    }
}