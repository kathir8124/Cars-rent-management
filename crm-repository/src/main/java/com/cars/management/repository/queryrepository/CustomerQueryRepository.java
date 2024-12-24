package com.cars.management.repository.queryrepository;

import com.cars.management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerQueryRepository extends JpaRepository<Customer, Integer> {
}
