package com.example.labtest1.controller;


import com.example.labtest1.model.Person;
import com.example.labtest1.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @PostMapping("/create")
    public ResponseEntity<Person> createPerson(@RequestBody Person person){
        Person savedPerson = personService.createPerson(person);

        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id){
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable String id,@RequestBody Person person){
        return personService.updatePerson(id,person).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable String id){
        if(personService.deletePerson(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

}