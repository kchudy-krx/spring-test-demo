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
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "cars")
    private List<Person> personList = new ArrayList<>();

    public Car(String name) {
        this.name = name;
    }
}
