package com.cars.management.repository.queryrepository;

import com.cars.management.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarQueryRepository extends JpaRepository<Car, Integer> {
    @Query(value = "SELECT * FROM cars WHERE status = :status", nativeQuery = true)
    List<Car> findByStatus(@Param("status") String status);
}
