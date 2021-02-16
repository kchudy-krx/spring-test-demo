package com.isa.resttest.service;

import com.isa.resttest.dto.PersonDTO;
import com.isa.resttest.model.Person;
import com.isa.resttest.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public PersonDTO savePerson(PersonDTO personDTO) {

        Person person = new Person(personDTO.getName(), personDTO.getLastName());

        person = personRepository.save(person);

        return new PersonDTO(person.getId(), person.getName(), person.getLastName());

    }
}
