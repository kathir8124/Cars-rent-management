package com.cars.management.repository.commandrepository;

import com.cars.management.entity.Lease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaseCommandRepository extends JpaRepository<Lease, Integer> {
}
