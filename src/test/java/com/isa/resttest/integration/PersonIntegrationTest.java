package com.isa.resttest.integration;

import com.isa.resttest.dto.PersonDTO;
import com.isa.resttest.model.Person;
import com.isa.resttest.repository.PersonRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.fail;

@AutoConfigureMockMvc
class PersonIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository personRepository;

    @LocalServerPort
    int port;

    @BeforeEach
    public void before() {
        RestAssured.port = port;
    }

    @AfterEach
    public void afterEach() {
        personRepository.deleteAll();
    }

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://localhost";
    }


    private Person savePerson(String name, String lastName) {

        return new Person(name, lastName);

    }

    @Test
    void deletePerson() {

        //given
        Person person = savePerson("Janek", "Kowalski");
        person = personRepository.save(person);

        //when
        Response delete = when().delete(String.format("/person/%s", person.getId().toString()));

        //then
        Assertions.assertEquals(204, delete.getStatusCode());

        Optional<Person> fromDb = personRepository.findById(person.getId());

        Assertions.assertTrue(fromDb.isEmpty());

    }

    @Test
    void getPersonList() {

        //given
        List<Person> toSave = new ArrayList<>();
        for (int a = 0; a < 1000; a++) {
            Person person = savePerson("name" + a, "lastName" + a);
            person = personRepository.save(person);
            toSave.add(person);
        }

        //when
        Response response = with().contentType(ContentType.JSON)
                .when().get("/person");

        //then
        PersonDTO[] result = response.body().as(PersonDTO[].class);

        List<PersonDTO> resultList = Arrays.stream(result).sorted(Comparator.comparing(PersonDTO::getName)).collect(Collectors.toList());
        List<PersonDTO> input = toSave.stream().map(person -> new PersonDTO(person.getId(), person.getName(), person.getLastName())).sorted(Comparator.comparing(PersonDTO::getName)).collect(Collectors.toList());

        Assertions.assertArrayEquals(resultList.toArray(), input.toArray());

    }


    @Test
    void newPersonTest() {

        //e2e test
        //bdd -> Behavior Driven Development
        //Given I have a Person with name Janek and lastName Kowalski
        //When I try to post it to system
        //Then person is saved

        //given
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("Jan");
        personDTO.setLastName("Kowalski");

        //when
        Response response = with().body(personDTO)
                .contentType(ContentType.JSON)
                .when().post("/person");

        //then

        Assertions.assertEquals(200, response.getStatusCode());

        try {
            PersonDTO result = response.body().as(PersonDTO.class);

            Assertions.assertEquals(personDTO.getLastName(), result.getLastName());
            Assertions.assertEquals(personDTO.getName(), result.getName());
            Assertions.assertNotNull(result.getId());
        } catch (Exception e) {
            fail(e);
        }


/*        PersonDTO personDTO = new PersonDTO();
        personDTO.setLastName("Kowalski");
        personDTO.setName("Jan");

        Response response = with().body(personDTO)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/person");

        Assertions.assertEquals(200, response.statusCode());

        try {
            PersonDTO result = response.body().as(PersonDTO.class);
            Assertions.assertEquals(personDTO.getName(), result.getName());
            Assertions.assertEquals(personDTO.getLastName(), result.getLastName());
        } catch (Exception e) {
            fail(e);
        }*/
    }


}
