package com.isa.resttest.service;

import com.isa.resttest.dto.CarDTO;
import com.isa.resttest.model.Car;
import com.isa.resttest.repository.CarRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional
    public CarDTO saveCar(CarDTO carDTO) {
        Car car = new Car(carDTO.getName());

        car = carRepository.save(car);

        return new CarDTO(car.getId(), car.getName());
    }

}
