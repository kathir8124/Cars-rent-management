package com.cars.management.service.serviveimpl.queryimpl;

import com.cars.management.dto.request.CarDto;
import com.cars.management.dto.request.LeaseDto;
import com.cars.management.entity.Car;
import com.cars.management.entity.Customer;
import com.cars.management.entity.Lease;
import com.cars.management.entity.Owner;
import com.cars.management.enums.CarStatus;
import com.cars.management.enums.LeaseStatus;
import com.cars.management.exception.ErrorException;
import com.cars.management.repository.queryrepository.CarQueryRepository;
import com.cars.management.repository.queryrepository.LeaseQueryRepository;
import com.cars.management.repository.queryrepository.OwnerQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {OwnerQueryServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class OwnerQueryServiceImplTest {
    @MockBean
    private CarQueryRepository carQueryRepository;

    @MockBean
    private LeaseQueryRepository leaseQueryRepository;

    @MockBean
    private OwnerQueryRepository ownerQueryRepository;

    @Autowired
    private OwnerQueryServiceImpl ownerQueryServiceImpl;

    /**
     * Method under test: {@link OwnerQueryServiceImpl#getOwnerById(Integer)}
     */
    @Test
    void testGetOwnerById2() {
        // Arrange
        when(ownerQueryRepository.findById(Mockito.<Integer>any()))
                .thenThrow(ErrorException.unauthorized("An error occurred"));

        // Act and Assert
        assertThrows(ErrorException.class, () -> ownerQueryServiceImpl.getOwnerById(1));
        verify(ownerQueryRepository).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link OwnerQueryServiceImpl#getLeaseHistoryByOwnerId(Integer)}
     */
    @Test
    void testGetLeaseHistoryByOwnerId() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        List<LeaseDto> actualLeaseHistoryByOwnerId = ownerQueryServiceImpl.getLeaseHistoryByOwnerId(1);

        // Assert
        verify(ownerQueryRepository).findById(eq(1));
        assertTrue(actualLeaseHistoryByOwnerId.isEmpty());
    }

    /**
     * Method under test:
     * {@link OwnerQueryServiceImpl#getLeaseHistoryByOwnerId(Integer)}
     */
    @Test
    void testGetLeaseHistoryByOwnerId2() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch lease history for owner with ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch lease history for owner with ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch lease history for owner with ID: {}");

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(car);

        Owner owner2 = new Owner();
        owner2.setCars(cars);
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1);
        owner2.setName("Name");
        owner2.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner2);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        List<LeaseDto> actualLeaseHistoryByOwnerId = ownerQueryServiceImpl.getLeaseHistoryByOwnerId(1);

        // Assert
        verify(ownerQueryRepository).findById(eq(1));
        assertTrue(actualLeaseHistoryByOwnerId.isEmpty());
    }

    /**
     * Method under test:
     * {@link OwnerQueryServiceImpl#getLeaseHistoryByOwnerId(Integer)}
     */
    @Test
    void testGetLeaseHistoryByOwnerId3() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch lease history for owner with ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch lease history for owner with ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch lease history for owner with ID: {}");

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2);
        owner2.setName("Owner found: {} - {}");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Owner found: {} - {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Owner found: {} - {}");

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(car2);
        cars.add(car);

        Owner owner3 = new Owner();
        owner3.setCars(cars);
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1);
        owner3.setName("Name");
        owner3.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner3);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        List<LeaseDto> actualLeaseHistoryByOwnerId = ownerQueryServiceImpl.getLeaseHistoryByOwnerId(1);

        // Assert
        verify(ownerQueryRepository).findById(eq(1));
        assertTrue(actualLeaseHistoryByOwnerId.isEmpty());
    }

    /**
     * Method under test:
     * {@link OwnerQueryServiceImpl#getLeaseHistoryByOwnerId(Integer)}
     */
    @Test
    void testGetLeaseHistoryByOwnerId4() {
        // Arrange
        when(ownerQueryRepository.findById(Mockito.<Integer>any()))
                .thenThrow(ErrorException.unauthorized("An error occurred"));

        // Act and Assert
        assertThrows(ErrorException.class, () -> ownerQueryServiceImpl.getLeaseHistoryByOwnerId(1));
        verify(ownerQueryRepository).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link OwnerQueryServiceImpl#getLeaseHistoryByOwnerId(Integer)}
     */
    @Test
    void testGetLeaseHistoryByOwnerId5() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch lease history for owner with ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch lease history for owner with ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch lease history for owner with ID: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch lease history for owner with ID: {}");
        customer.setPhoneNumber("6625550144");

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setId(1);
        lease.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setStatus(LeaseStatus.ACTIVE);

        ArrayList<Lease> leases = new ArrayList<>();
        leases.add(lease);

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1);
        owner2.setName("Received request to fetch lease history for owner with ID: {}");
        owner2.setPhoneNumber("6625550144");

        Car car2 = new Car();
        car2.setId(1);
        car2.setLeases(leases);
        car2.setModel("Received request to fetch lease history for owner with ID: {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.IDLE);
        car2.setVariant("Received request to fetch lease history for owner with ID: {}");

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(car2);

        Owner owner3 = new Owner();
        owner3.setCars(cars);
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1);
        owner3.setName("Name");
        owner3.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner3);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        List<LeaseDto> actualLeaseHistoryByOwnerId = ownerQueryServiceImpl.getLeaseHistoryByOwnerId(1);

        // Assert
        verify(ownerQueryRepository).findById(eq(1));
        assertEquals(1, actualLeaseHistoryByOwnerId.size());
        LeaseDto getResult = actualLeaseHistoryByOwnerId.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        CarDto carDto = getResult.getCarDto();
        assertEquals("Received request to fetch lease history for owner with ID: {}", carDto.getModel());
        assertEquals("Received request to fetch lease history for owner with ID: {}", carDto.getVariant());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult.getStatus());
    }
}
