package com.isa.resttest.service;

import com.isa.resttest.dto.CarDTO;
import com.isa.resttest.dto.PersonDTO;
import com.isa.resttest.model.Car;
import com.isa.resttest.model.Person;
import com.isa.resttest.repository.CarRepository;
import com.isa.resttest.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final CarRepository carRepository;

    public PersonService(PersonRepository personRepository, CarRepository carRepository) {
        this.personRepository = personRepository;
        this.carRepository = carRepository;
    }

    @Transactional
    public PersonDTO savePerson(PersonDTO personDTO) {

        Person person = new Person(personDTO.getName(), personDTO.getLastName());

        person = personRepository.save(person);

        return new PersonDTO(person.getId(), person.getName(), person.getLastName(), Collections.EMPTY_LIST);

    }


    @Transactional
    public List<PersonDTO> findAll() {

        return personRepository.findAll()
                .stream()
                .map(person -> new PersonDTO(person.getId(), person.getName(), person.getLastName(), person.getCars().stream().map(car -> new CarDTO(car.getId(), car.getName())).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePerson(UUID id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public PersonDTO addCarToPerson(UUID personId, UUID carID) {
        Person person = personRepository.getOne(personId);

        Car car = carRepository.getOne(carID);

        person.getCars().add(car);

        person = personRepository.save(person);

        return new PersonDTO(person.getId(), person.getName(), person.getLastName(), person.getCars().stream().map(c -> new CarDTO(c.getId(), c.getName())).collect(Collectors.toList()));

    }
}