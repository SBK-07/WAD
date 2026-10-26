package com.example.labtest1.service;

import com.example.labtest1.model.Person;
import com.example.labtest1.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> getAllPerson(){
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(String id){
        return personRepository.findById(id);
    }

    public Person createPerson(Person person){
        return personRepository.save(person);
    }

    public Optional<Person> updatePerson(String id,Person updatedPerson){
        return personRepository.findById(id)
                .map(existingPerson -> {
                    //existingPerson.setId(updatedPerson.getId());
                    existingPerson.setName(updatedPerson.getName());

                    return personRepository.save(existingPerson);
                        }

                );
    }

    public boolean deletePerson(String id){
        if(personRepository.existsById (id)){
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }
}