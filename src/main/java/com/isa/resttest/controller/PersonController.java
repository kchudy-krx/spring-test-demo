package com.isa.resttest.controller;

import com.isa.resttest.dto.PersonDTO;
import com.isa.resttest.service.PersonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    PersonDTO savePerson(@RequestBody PersonDTO personDTO) {
        return personService.savePerson(personDTO);

    }


}
