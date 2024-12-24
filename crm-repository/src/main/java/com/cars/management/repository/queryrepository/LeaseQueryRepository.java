package com.cars.management.repository.queryrepository;

import com.cars.management.entity.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaseQueryRepository extends JpaRepository<Lease, Integer> {

    @Query(value = "SELECT * FROM leases WHERE status = :status", nativeQuery = true)
    List<Lease> findByLeaseByStatus(@Param("status") String status);
    @Query(value = "SELECT * FROM leases WHERE status = :status AND customer_id = :customerId", nativeQuery = true)
    List<Lease> findByCustomerIdAndStatus(@Param("customerId") Integer customerId, @Param("status") String status);
}
