package com.cars.management.repository.commandrepository;

import com.cars.management.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerCommandRepository extends JpaRepository<Owner, Integer> {

}
