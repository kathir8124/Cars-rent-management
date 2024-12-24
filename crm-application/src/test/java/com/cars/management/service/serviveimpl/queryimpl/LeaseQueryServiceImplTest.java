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
import com.cars.management.repository.queryrepository.LeaseQueryRepository;
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

@ContextConfiguration(classes = {LeaseQueryServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LeaseQueryServiceImplTest {
    @MockBean
    private LeaseQueryRepository leaseQueryRepository;

    @Autowired
    private LeaseQueryServiceImpl leaseQueryServiceImpl;

    /**
     * Method under test: {@link LeaseQueryServiceImpl#getLeaseDetailsById(Integer)}
     */
    @Test
    void testGetLeaseDetailsById() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Model");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Variant");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setId(1);
        lease.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setStatus(LeaseStatus.ACTIVE);
        Optional<Lease> ofResult = Optional.of(lease);
        when(leaseQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        LeaseDto actualLeaseDetailsById = leaseQueryServiceImpl.getLeaseDetailsById(1);

        // Assert
        verify(leaseQueryRepository).findById(eq(1));
        assertEquals("1970-01-01", actualLeaseDetailsById.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", actualLeaseDetailsById.getStartDate().toLocalDate().toString());
        CarDto carDto = actualLeaseDetailsById.getCarDto();
        assertEquals("Model", carDto.getModel());
        assertEquals("Variant", carDto.getVariant());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, actualLeaseDetailsById.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, actualLeaseDetailsById.getStatus());
    }

    /**
     * Method under test: {@link LeaseQueryServiceImpl#getLeaseDetailsById(Integer)}
     */
    @Test
    void testGetLeaseDetailsById2() {
        // Arrange
        when(leaseQueryRepository.findById(Mockito.<Integer>any()))
                .thenThrow(ErrorException.unauthorized("An error occurred"));

        // Act and Assert
        assertThrows(ErrorException.class, () -> leaseQueryServiceImpl.getLeaseDetailsById(1));
        verify(leaseQueryRepository).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link LeaseQueryServiceImpl#getLeaseDetailsByStatus(LeaseStatus)}
     */
    @Test
    void testGetLeaseDetailsByStatus() {
        // Arrange
        when(leaseQueryRepository.findByLeaseByStatus(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<LeaseDto> actualLeaseDetailsByStatus = leaseQueryServiceImpl.getLeaseDetailsByStatus(LeaseStatus.ACTIVE);

        // Assert
        verify(leaseQueryRepository).findByLeaseByStatus(eq("ACTIVE"));
        assertTrue(actualLeaseDetailsByStatus.isEmpty());
    }

    /**
     * Method under test:
     * {@link LeaseQueryServiceImpl#getLeaseDetailsByStatus(LeaseStatus)}
     */
    @Test
    void testGetLeaseDetailsByStatus2() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch leases with status: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch leases with status: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch leases with status: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch leases with status: {}");
        customer.setPhoneNumber("6625550144");

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setId(1);
        lease.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setStatus(LeaseStatus.ACTIVE);

        ArrayList<Lease> leaseList = new ArrayList<>();
        leaseList.add(lease);
        when(leaseQueryRepository.findByLeaseByStatus(Mockito.<String>any())).thenReturn(leaseList);

        // Act
        List<LeaseDto> actualLeaseDetailsByStatus = leaseQueryServiceImpl.getLeaseDetailsByStatus(LeaseStatus.ACTIVE);

        // Assert
        verify(leaseQueryRepository).findByLeaseByStatus(eq("ACTIVE"));
        assertEquals(1, actualLeaseDetailsByStatus.size());
        LeaseDto getResult = actualLeaseDetailsByStatus.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        CarDto carDto = getResult.getCarDto();
        assertEquals("Received request to fetch leases with status: {}", carDto.getModel());
        assertEquals("Received request to fetch leases with status: {}", carDto.getVariant());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult.getStatus());
    }

    /**
     * Method under test:
     * {@link LeaseQueryServiceImpl#getLeaseDetailsByStatus(LeaseStatus)}
     */
    @Test
    void testGetLeaseDetailsByStatus3() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch leases with status: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch leases with status: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch leases with status: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch leases with status: {}");
        customer.setPhoneNumber("6625550144");

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setId(1);
        lease.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setStatus(LeaseStatus.ACTIVE);

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2);
        owner2.setName("Successfully retrieved {} leases with status: {}");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Successfully retrieved {} leases with status: {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Successfully retrieved {} leases with status: {}");

        Customer customer2 = new Customer();
        customer2.setEmail("john.smith@example.org");
        customer2.setId(2);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Successfully retrieved {} leases with status: {}");
        customer2.setPhoneNumber("8605550118");

        Lease lease2 = new Lease();
        lease2.setCar(car2);
        lease2.setCustomer(customer2);
        lease2.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setId(2);
        lease2.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setStatus(LeaseStatus.ENDED);

        ArrayList<Lease> leaseList = new ArrayList<>();
        leaseList.add(lease2);
        leaseList.add(lease);
        when(leaseQueryRepository.findByLeaseByStatus(Mockito.<String>any())).thenReturn(leaseList);

        // Act
        List<LeaseDto> actualLeaseDetailsByStatus = leaseQueryServiceImpl.getLeaseDetailsByStatus(LeaseStatus.ACTIVE);

        // Assert
        verify(leaseQueryRepository).findByLeaseByStatus(eq("ACTIVE"));
        assertEquals(2, actualLeaseDetailsByStatus.size());
        LeaseDto getResult = actualLeaseDetailsByStatus.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        LeaseDto getResult2 = actualLeaseDetailsByStatus.get(1);
        assertEquals("1970-01-01", getResult2.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult2.getStartDate().toLocalDate().toString());
        CarDto carDto = getResult2.getCarDto();
        assertEquals("Received request to fetch leases with status: {}", carDto.getModel());
        assertEquals("Received request to fetch leases with status: {}", carDto.getVariant());
        CarDto carDto2 = getResult.getCarDto();
        assertEquals("Successfully retrieved {} leases with status: {}", carDto2.getModel());
        assertEquals("Successfully retrieved {} leases with status: {}", carDto2.getVariant());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, getResult2.getId().intValue());
        assertEquals(2, carDto2.getId().intValue());
        assertEquals(2, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult2.getStatus());
        assertEquals(LeaseStatus.ENDED, getResult.getStatus());
    }

    /**
     * Method under test:
     * {@link LeaseQueryServiceImpl#getLeaseDetailsByStatus(LeaseStatus)}
     */
    @Test
    void testGetLeaseDetailsByStatus4() {
        // Arrange
        when(leaseQueryRepository.findByLeaseByStatus(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<LeaseDto> actualLeaseDetailsByStatus = leaseQueryServiceImpl.getLeaseDetailsByStatus(LeaseStatus.ENDED);

        // Assert
        verify(leaseQueryRepository).findByLeaseByStatus(eq("ENDED"));
        assertTrue(actualLeaseDetailsByStatus.isEmpty());
    }

    /**
     * Method under test: {@link LeaseQueryServiceImpl#getAllLeaseDetails()}
     */
    @Test
    void testGetAllLeaseDetails() {
        // Arrange
        when(leaseQueryRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<LeaseDto> actualAllLeaseDetails = leaseQueryServiceImpl.getAllLeaseDetails();

        // Assert
        verify(leaseQueryRepository).findAll();
        assertTrue(actualAllLeaseDetails.isEmpty());
    }

    /**
     * Method under test: {@link LeaseQueryServiceImpl#getAllLeaseDetails()}
     */
    @Test
    void testGetAllLeaseDetails2() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch all lease details.");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch all lease details.");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch all lease details.");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch all lease details.");
        customer.setPhoneNumber("6625550144");

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setId(1);
        lease.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setStatus(LeaseStatus.ACTIVE);

        ArrayList<Lease> leaseList = new ArrayList<>();
        leaseList.add(lease);
        when(leaseQueryRepository.findAll()).thenReturn(leaseList);

        // Act
        List<LeaseDto> actualAllLeaseDetails = leaseQueryServiceImpl.getAllLeaseDetails();

        // Assert
        verify(leaseQueryRepository).findAll();
        assertEquals(1, actualAllLeaseDetails.size());
        LeaseDto getResult = actualAllLeaseDetails.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        CarDto carDto = getResult.getCarDto();
        assertEquals("Received request to fetch all lease details.", carDto.getModel());
        assertEquals("Received request to fetch all lease details.", carDto.getVariant());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult.getStatus());
    }

    /**
     * Method under test: {@link LeaseQueryServiceImpl#getAllLeaseDetails()}
     */
    @Test
    void testGetAllLeaseDetails3() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch all lease details.");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch all lease details.");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch all lease details.");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch all lease details.");
        customer.setPhoneNumber("6625550144");

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setId(1);
        lease.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setStatus(LeaseStatus.ACTIVE);

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2);
        owner2.setName("Successfully fetched {} lease details.");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Successfully fetched {} lease details.");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Successfully fetched {} lease details.");

        Customer customer2 = new Customer();
        customer2.setEmail("john.smith@example.org");
        customer2.setId(2);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Successfully fetched {} lease details.");
        customer2.setPhoneNumber("8605550118");

        Lease lease2 = new Lease();
        lease2.setCar(car2);
        lease2.setCustomer(customer2);
        lease2.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setId(2);
        lease2.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setStatus(LeaseStatus.ENDED);

        ArrayList<Lease> leaseList = new ArrayList<>();
        leaseList.add(lease2);
        leaseList.add(lease);
        when(leaseQueryRepository.findAll()).thenReturn(leaseList);

        // Act
        List<LeaseDto> actualAllLeaseDetails = leaseQueryServiceImpl.getAllLeaseDetails();

        // Assert
        verify(leaseQueryRepository).findAll();
        assertEquals(2, actualAllLeaseDetails.size());
        LeaseDto getResult = actualAllLeaseDetails.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        LeaseDto getResult2 = actualAllLeaseDetails.get(1);
        assertEquals("1970-01-01", getResult2.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult2.getStartDate().toLocalDate().toString());
        CarDto carDto = getResult2.getCarDto();
        assertEquals("Received request to fetch all lease details.", carDto.getModel());
        assertEquals("Received request to fetch all lease details.", carDto.getVariant());
        CarDto carDto2 = getResult.getCarDto();
        assertEquals("Successfully fetched {} lease details.", carDto2.getModel());
        assertEquals("Successfully fetched {} lease details.", carDto2.getVariant());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, getResult2.getId().intValue());
        assertEquals(2, carDto2.getId().intValue());
        assertEquals(2, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult2.getStatus());
        assertEquals(LeaseStatus.ENDED, getResult.getStatus());
    }
}
