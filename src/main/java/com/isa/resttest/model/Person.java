package com.isa.resttest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Car> cars = new ArrayList<>();

    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }


}
