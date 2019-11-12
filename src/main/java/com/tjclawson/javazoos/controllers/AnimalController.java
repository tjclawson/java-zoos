package com.tjclawson.javazoos.controllers;

import com.tjclawson.javazoos.logging.Loggable;
import com.tjclawson.javazoos.services.AnimalService;
import com.tjclawson.javazoos.views.AnimalCountZoos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Loggable
@RestController
@RequestMapping(value = "/animals")
public class AnimalController {

    private AnimalService animalService;
    private static final Logger logger = LoggerFactory.getLogger(AnimalController.class);

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping(value = "/count", produces = {"application/json"})
    public ResponseEntity<?> getAnimalCounts(HttpServletRequest request) {
        String stringDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(new Date().getTime());
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed at " + stringDate);
        return new ResponseEntity<>(animalService.getCountAnimalZoos(), HttpStatus.OK);
    }
}
