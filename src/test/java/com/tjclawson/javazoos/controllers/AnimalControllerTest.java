package com.tjclawson.javazoos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjclawson.javazoos.models.Animal;
import com.tjclawson.javazoos.models.Zoo;
import com.tjclawson.javazoos.models.ZooAnimals;
import com.tjclawson.javazoos.services.AnimalService;
import com.tjclawson.javazoos.services.ZooService;
import com.tjclawson.javazoos.views.AnimalCountZoos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    private List<AnimalCountZoos> animalCountZoos = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        animalCountZoos.add(new AnimalCountZoos() {
            @Override
            public String getAnimaltype() {
                return "Animal 1";
            }

            @Override
            public int getCountanimal() {
                return 1;
            }
        });
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAnimalCounts() throws Exception {
        String apiUrl = "/animals/count";

        Mockito.when(animalService.getCountAnimalZoos()).thenReturn(animalCountZoos);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String trueResponse = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expectedResponse = mapper.writeValueAsString(animalCountZoos);

        assertEquals(expectedResponse, trueResponse);
    }
}