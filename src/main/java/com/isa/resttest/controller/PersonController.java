package com.isa.resttest.controller;

import com.isa.resttest.dto.PersonDTO;
import com.isa.resttest.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    List<PersonDTO> findAll() {
        return personService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePerson(@PathVariable UUID id) {

        personService.deletePerson(id);

    }


}
