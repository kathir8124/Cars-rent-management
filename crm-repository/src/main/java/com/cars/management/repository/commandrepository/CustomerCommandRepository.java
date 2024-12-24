package com.cars.management.repository.commandrepository;

import com.cars.management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCommandRepository extends JpaRepository<Customer, Integer> {
}
