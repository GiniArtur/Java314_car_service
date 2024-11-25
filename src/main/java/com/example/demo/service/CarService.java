package com.example.demo.service;


import com.example.demo.Car;
import com.example.demo.dto.ProduceCountDto;
import com.example.demo.dto.ProduceDto;
import com.example.demo.repo.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> list() {
        return carRepository.findAll();
    }




    public List<Car> findByModel(String model) {
        if (model != null) {
            return carRepository.findByModel(model);
        }
        return carRepository.findAll();
    }

    public List<ProduceCountDto> getCountByProduce() {
        List<Object[]> results = carRepository.countByProduce();
        List<ProduceCountDto> produceCounts = new ArrayList<>();

        for (Object[] result : results) {
            String produce = (String) result[0]; // Производитель
            Long count = ((Number) result[1]).longValue(); // Количество
            produceCounts.add(new ProduceCountDto(produce, count));
        }
        return produceCounts;
    }
    public List<ProduceDto> getProduces() {
        List<Object[]> results = carRepository.findAllProduces();
        List<ProduceDto> produces = new ArrayList<>();

        for (Object[] result : results) {
            String produce = (String) result[0]; // Производитель

            produces.add(new ProduceDto(produce));
        }
        return produces;
    }


    public Object[] getProduceTop() {
        List<Object[]> producersWithCounts = carRepository.findTopProduce();

        if (producersWithCounts.isEmpty()) {
            throw new RuntimeException("Производителей не найдено");
        }
        // Возвращаем производителя с максимальным количеством машин
        return producersWithCounts.stream()
                .max(Comparator.comparingLong(o -> ((Number) o[1]).longValue())) // Сравниваем по количеству машин
                .orElseThrow(() -> new RuntimeException("Ошибка поиска производителя с максимальным количеством машин"));


    }

    public Object[] getProduceLeast() {
        List<Object[]> producersWithCounts = carRepository.findLeastProduce();

        if (producersWithCounts.isEmpty()) {
            throw new RuntimeException("Производителей не найдено");
        }
        // Возвращаем производителя с максимальным количеством машин
        return producersWithCounts.stream()
                .min(Comparator.comparingLong(o -> ((Number) o[1]).longValue())) // Сравниваем по количеству машин
                .orElseThrow(() -> new RuntimeException("Ошибка поиска производителя с максимальным количеством машин"));


    }
}




