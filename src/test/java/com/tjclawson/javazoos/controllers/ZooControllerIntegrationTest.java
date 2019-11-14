package com.tjclawson.javazoos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjclawson.javazoos.models.Zoo;
import com.tjclawson.javazoos.models.ZooAnimals;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.number.OrderingComparison.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZooControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void whenMeasuredReponseTime() {
        given().when().get("/zoos/zoos").then().time(lessThan(5000L));
    }

    @Test
    public void givenPostAZoo() throws Exception {
        ArrayList<ZooAnimals> zooAnimals = new ArrayList<>();
        Zoo myZoo = new Zoo("myZoo", zooAnimals);

        ObjectMapper mapper = new ObjectMapper();
        String zooString = mapper.writeValueAsString(myZoo);

        given().contentType("application/json").body(zooString).when().post("/zoos/zoo").then().statusCode(201);
    }

    @Test
    public void givenFoundZooId() throws Exception {
        given().when().get("/zoos/zoo/1").then().statusCode(200).and().body(containsString("bear"));
    }

    @Test
    public void givenFoundZooName() throws Exception {
        given().when().get("/zoos/zoo/namelike/Gladys").then().statusCode(200).and().body(containsString("Porter"));
    }

    @Test
    public void givenFindAllZoos() throws Exception {
        given().when().get("/zoos/zoos").then().statusCode(200).and().body(containsString("Porter"));
    }

    @Test
    public void givenUpdateZoo() throws Exception {
        ArrayList<ZooAnimals> zooAnimals = new ArrayList<>();
        Zoo myZoo = new Zoo("myZoo", zooAnimals);

        ObjectMapper mapper = new ObjectMapper();
        String zooString = mapper.writeValueAsString(myZoo);

        given().contentType("application/json").body(zooString).when().put("/zoos/zoo/1").then().statusCode(200);
    }

    @Test
    public void givenDeleteZoo() throws Exception {
        given().when().delete("zoos/zoo/1").then().statusCode(200);
    }
}
