package com.example.demo.controller;

import com.example.demo.Car;
import com.example.demo.dto.ProduceCountDto;
import com.example.demo.repo.CarRepository;
import com.example.demo.service.CarService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@Data

public class CarController {
    private final CarRepository carRepository;
    private final CarService carService;

    @GetMapping("/car")
    public String listCars(Model model) {
        return model.addAttribute("cars", carService.list()).toString();
    }

    @GetMapping("/car/color/{color}")
    public List<Car> getCarByColor(@PathVariable String color) {
        return carRepository.findByColor(color);
    }

    @PostMapping("/car/new")
    public Car addCar(@RequestBody Car car) {
        return carRepository.save(car);
    }

    @GetMapping("/car/year/{year}")
    public List<Car> getCarByYear(@PathVariable int year) {
        return carRepository.findByYear(year);
    }

    @GetMapping("/car/type/{type}")
    public List<Car> getCarByType(@PathVariable String type) {
        return carRepository.findByType(type);
    }

    @GetMapping("/car/engineCapacity/{engineCapacity}")
    public List<Car> getCarByEngineCapacity(@PathVariable double engineCapacity) {
        return carRepository.findByEngineCapacity(engineCapacity);
    }

    @PutMapping("/car/update/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автомобиль не найден"));
        car.setProduce(updatedCar.getProduce());
        car.setModel(updatedCar.getModel());
        car.setEngineCapacity(updatedCar.getEngineCapacity());
        car.setYear(updatedCar.getYear());
        car.setColor(updatedCar.getColor());
        car.setType(updatedCar.getType());
        return carRepository.save(car);
    }

    @GetMapping("/car/produces")
    public List<Object[]> getProduces() {
        return carRepository.findAllProduces();
    }

    @GetMapping("/car/produce/{produce}")
    public List<Car> getCarByProduce(@PathVariable String produce) {
        return carRepository.findByProduce(produce);
    }

    @GetMapping("/car/model/{model}")
    public List<Car> getCarByModel(@PathVariable String model) {
        return carRepository.findByModel(model);
    }

    @GetMapping("/car/between/{startYear},{endYear}")
    public List<Car> getCarByYearBetween(@PathVariable int startYear, @PathVariable int endYear) {
        return carRepository.findByYearBetween(startYear, endYear);
    }

    @GetMapping("/produce-count")
    public List<ProduceCountDto> getProduceCounts() {
        System.out.println("Количество производителей");
        return carService.getCountByProduce();
    }

    @GetMapping("/produce-top")
    public Object[] getTopProduce() {
        System.out.println("Производитель с максимальным количеством машин");
        return carService.getProduceTop();
    }

    @GetMapping("/produce-least")
    public Object[] getLeastProduce() {
        System.out.println("Производитель с минимальным количеством машин");
        return carService.getProduceLeast();
    }
}
