package com.isa.resttest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.resttest.dto.PersonDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.fail;

@AutoConfigureMockMvc
class PersonIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    int port;

    @BeforeEach
    public void before() {
        RestAssured.port = port;
    }

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    void newPersonTest() {

        PersonDTO personDTO = new PersonDTO();
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
        }
    }


}
