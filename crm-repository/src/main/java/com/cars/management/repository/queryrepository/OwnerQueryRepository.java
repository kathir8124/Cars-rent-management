package com.cars.management.repository.queryrepository;


import com.cars.management.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerQueryRepository extends JpaRepository<Owner, Integer> {
//    @Query(value = "SELECT \n" +
//            "    o.owner_id AS ownerId, \n" +
//            "    c.car_id AS carId,\n" +
//            "    l.lease_id AS leaseId, \n" +
//            "    l.status AS status, \n" +
//            "    c.variant AS variant, \n" +
//            "    c.model AS model, \n" +
//            "    l.start_date AS startDate, \n" +
//            "    l.end_date AS endDate \n" +
//            "FROM \n" +
//            "    owners o \n" +
//            "INNER JOIN \n" +
//            "    cars c ON o.owner_id = c.owner_id \n" +
//            "INNER JOIN \n" +
//            "    leases l ON c.car_id = l.car_id\n" +
//            "WHERE o.owner_id = :ownerId", nativeQuery = true)
//    List<OwnerCarLeaseResponseDto> findOwnerCarLeaseDetails(@Param("ownerId") Integer ownerId);
}
