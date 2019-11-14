package com.tjclawson.javazoos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjclawson.javazoos.models.Telephone;
import com.tjclawson.javazoos.models.Zoo;
import com.tjclawson.javazoos.models.ZooAnimals;
import com.tjclawson.javazoos.services.ZooService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = ZooController.class)
public class ZooControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ZooService zooService;

    private List<Zoo> zooList;
    private List<ZooAnimals> zooAnimalsList;

    @Before
    public void setUp() throws Exception {

        zooAnimalsList = new ArrayList<>();
        zooList = new ArrayList<>();
        Zoo zoo1 = new Zoo("Zoo 1", zooAnimalsList);
        zoo1.setZooid(1);
        Zoo zoo2 = new Zoo("Zoo 2", zooAnimalsList);
        zoo2.setZooid(2);
        zooList.add(zoo1);
        zooList.add(zoo2);

        Telephone tele1 = new Telephone("tele 1", "000-000-0000", zoo1);
        Telephone tele2 = new Telephone("tele 2", "111-111-1111", zoo2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listAllZoos() throws Exception {

        String apiUrl = "/zoos/zoos";

        Mockito.when(zooService.findAll()).thenReturn(zooList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String testResult = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expectedResult = mapper.writeValueAsString(zooList);

        assertEquals(expectedResult, testResult);
    }

    @Test
    public void getZooById() throws Exception {
        String apiUrl = "/zoos/zoo/1";

        Mockito.when(zooService.findZooById(1)).thenReturn(zooList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(zooList.get(0));

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getZooByNamelike() throws Exception {
        String apiUrl = "/zoos/zoo/namelike/Zoo";

        Mockito.when(zooService.findByNameContaining("Zoo")).thenReturn(zooList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String testResult = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expectedResult = mapper.writeValueAsString(zooList);

        assertEquals(expectedResult, testResult);
    }

    @Test
    public void addNewZoo() throws Exception {
        String apiUrl = "/zoos/zoo";

        Zoo newZoo = new Zoo("New Zoo", zooAnimalsList);
        newZoo.setZooid(100);
        ObjectMapper mapper = new ObjectMapper();
        String zooString = mapper.writeValueAsString(newZoo);

        Mockito.when(zooService.save(any(Zoo.class))).thenReturn(newZoo);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(zooString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateZoo() throws Exception {
        String apiUrl = "/zoos/zoo/{zooid}";

        Zoo updateZoo = new Zoo("myZoo", zooAnimalsList);
        updateZoo.setZooid(50);

        Mockito.when(zooService.update(updateZoo, 50)).thenReturn(updateZoo);

        ObjectMapper mapper = new ObjectMapper();
        String zooString = mapper.writeValueAsString(updateZoo);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 50L)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(zooString);

        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void deleteZooById() throws Exception {

        String apiUrl = "/zoos/zoo/{zooid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteZooAnimalByIds() throws Exception {
        String apiUrl = "/zoos/zoo/{zooid}/animals/{animalid}";
        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "1", "1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void postZooAnimalByIds() throws Exception {
        String apiUrl = "/zoos/zoo/{zooid}/animals/{animalid}";
        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl, "1", "1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }
}