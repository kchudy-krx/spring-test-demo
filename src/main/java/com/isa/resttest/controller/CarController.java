package com.isa.resttest.controller;

import com.isa.resttest.dto.CarDTO;
import com.isa.resttest.service.CarService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    CarDTO saveCar(CarDTO carDTO) {
        return carService.saveCar(carDTO);
    }

}
