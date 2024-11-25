package com.example.demo.repo;

import com.example.demo.Car;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CarRepository extends JpaRepository<Car, Long> {
    @NonNull
    List<Car> findAll();

    List<Car> findByModel(String model);

    List<Car> findByEngineCapacity(double engineCapacity);


    List<Car> findByProduce(String produce);

    List<Car> findByYear(int year);

    List<Car> findByYearBetween(int startYear, int endYear);

    List<Car> findByColor(String color);

    List<Car> findByType(String type);

    @Query("SELECT c.produce, COUNT(c) FROM Car c GROUP BY c.produce")
        //JPQL оперирует именами полей и
        // сущностей, а не колонок базы данных
    List<Object[]> countByProduce();

    @Query("SELECT с.produce, COUNT(с) FROM Car с GROUP BY с.produce ORDER BY COUNT(*) DESC")
    List<Object[]> findTopProduce();

    @Query("SELECT с.produce, COUNT(с) FROM Car с GROUP BY с.produce ORDER BY COUNT(*) ASC")
    List<Object[]> findLeastProduce();

    @Query("SELECT DISTINCT c.produce FROM Car c")
    List<Object[]> findAllProduces();
}



