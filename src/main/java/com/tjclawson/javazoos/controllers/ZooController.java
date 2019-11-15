package com.tjclawson.javazoos.controllers;

import com.tjclawson.javazoos.logging.Loggable;
import com.tjclawson.javazoos.models.Zoo;
import com.tjclawson.javazoos.services.ZooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Loggable
@RestController
@RequestMapping("/zoos")
public class ZooController {

    private ZooService zooService;
    private static final Logger logger = LoggerFactory.getLogger(AnimalController.class);

    public ZooController(ZooService zooService) {
        this.zooService = zooService;
    }

    //localhost:2019/zoos/zoos
    @GetMapping(value = "/zoos", produces = {"application/json"})
    public ResponseEntity<?> listAllZoos(HttpServletRequest request, @PageableDefault(page = 0, size = 5) Pageable pageable) {
        String stringDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(new Date().getTime());
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed at " + stringDate);
        List<Zoo> myZoos = zooService.findAll();
        return new ResponseEntity<>(myZoos, HttpStatus.OK);
    }

    //localhost:2019/zoos/zoo/{id}
    @GetMapping(value = "/zoo/{zooid}", produces = {"application/json"})
    public ResponseEntity<?> getZooById(@PathVariable long zooid, HttpServletRequest request) {
        String stringDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(new Date().getTime());
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed at " + stringDate);;
        Zoo myZoo = zooService.findZooById(zooid);
        return new ResponseEntity<>(myZoo, HttpStatus.OK);
    }

    //localhost:2019/zoos/zoo/namelike/{name}
    @GetMapping(value = "/zoo/namelike/{name}", produces = {"application/json"})
    public ResponseEntity<?> getZooByNamelike(@PathVariable String name, HttpServletRequest request) {
        String stringDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(new Date().getTime());
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed at " + stringDate);
        List<Zoo> myZoos = zooService.findByNameContaining(name);
        return new ResponseEntity<>(myZoos, HttpStatus.OK);
    }

    //localhost:2019/zoos/zoo -- POST
    @PostMapping(value = "/zoo", consumes = {"application/json"})
    public ResponseEntity<?> addNewZoo(@Valid @RequestBody Zoo newZoo) {
        newZoo = zooService.save(newZoo);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newZooURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{zooid}")
                .buildAndExpand(newZoo.getZooid())
                .toUri();
        responseHeaders.setLocation(newZooURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    //localhost:2019/zoos/zoo/{id} -- PUT
    @PutMapping(value = "/zoo/{zooid}", consumes = {"application/json"})
    public ResponseEntity<?> updateZoo(@RequestBody Zoo updateZoo, @PathVariable long zooid) {
        zooService.update(updateZoo, zooid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //localhost:2019/zoos/zoo/{id} -- DELETE
    @DeleteMapping(value = "/zoo/{zooid}")
    public ResponseEntity<?> deleteZooById(@PathVariable long zooid) {
        zooService.delete(zooid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //localhost:2019/zoos/zoo/{zooid}/animals/{animalid} -- DELETE
    @DeleteMapping(value = "/zoo/{zooid}/animals/{animalid}")
    public ResponseEntity<?> deleteZooAnimalByIds(@PathVariable long zooid, @PathVariable long animalid) {
        zooService.deleteZooAnimal(zooid, animalid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //localhost:2019/zoos/zoo/{zooid}/animals/{animalid} -- POST
    @PostMapping(value = "/zoo/{zooid}/animals/{animalid}")
    public ResponseEntity postZooAnimalByIds(@PathVariable long zooid, @PathVariable long animalid) {
        zooService.addZooAnimal(zooid, animalid);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
