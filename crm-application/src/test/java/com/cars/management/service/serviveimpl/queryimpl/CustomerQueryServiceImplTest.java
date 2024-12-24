package com.cars.management.service.serviveimpl.queryimpl;

import com.cars.management.dto.request.CarDto;
import com.cars.management.dto.request.CustomerDto;
import com.cars.management.dto.request.LeaseDto;
import com.cars.management.dto.response.CarResponseDto;
import com.cars.management.entity.Car;
import com.cars.management.entity.Customer;
import com.cars.management.entity.Lease;
import com.cars.management.entity.Owner;
import com.cars.management.enums.CarStatus;
import com.cars.management.enums.LeaseStatus;
import com.cars.management.exception.ErrorException;
import com.cars.management.repository.queryrepository.CarQueryRepository;
import com.cars.management.repository.queryrepository.CustomerQueryRepository;
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

@ContextConfiguration(classes = {CustomerQueryServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CustomerQueryServiceImplTest {
    @MockBean
    private CarQueryRepository carQueryRepository;

    @MockBean
    private CustomerQueryRepository customerQueryRepository;

    @Autowired
    private CustomerQueryServiceImpl customerQueryServiceImpl;

    @MockBean
    private LeaseQueryRepository leaseQueryRepository;

    /**
     * Method under test: {@link CustomerQueryServiceImpl#getCustomerById(Integer)}
     */
    @Test
    void testGetCustomerById() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        ArrayList<Lease> leases = new ArrayList<>();
        customer.setLeases(leases);
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        CustomerDto actualCustomerById = customerQueryServiceImpl.getCustomerById(1);

        // Assert
        verify(customerQueryRepository).findById(eq(1));
        assertEquals("6625550144", actualCustomerById.getPhoneNumber());
        assertEquals("Name", actualCustomerById.getName());
        assertEquals("jane.doe@example.org", actualCustomerById.getEmail());
        assertEquals(1, actualCustomerById.getId().intValue());
        assertEquals(leases, actualCustomerById.getLeaseDtoList());
    }

    /**
     * Method under test: {@link CustomerQueryServiceImpl#getCustomerById(Integer)}
     */
    @Test
    void testGetCustomerById2() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch customer with ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch customer with ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch customer with ID: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch customer with ID: {}");
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

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1);
        customer2.setLeases(leases);
        customer2.setName("Name");
        customer2.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer2);
        when(customerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        CustomerDto actualCustomerById = customerQueryServiceImpl.getCustomerById(1);

        // Assert
        verify(customerQueryRepository).findById(eq(1));
        List<LeaseDto> leaseDtoList = actualCustomerById.getLeaseDtoList();
        assertEquals(1, leaseDtoList.size());
        LeaseDto getResult = leaseDtoList.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        assertEquals("6625550144", actualCustomerById.getPhoneNumber());
        assertEquals("Name", actualCustomerById.getName());
        CarDto carDto = getResult.getCarDto();
        assertEquals("Received request to fetch customer with ID: {}", carDto.getModel());
        assertEquals("Received request to fetch customer with ID: {}", carDto.getVariant());
        assertEquals("jane.doe@example.org", actualCustomerById.getEmail());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, actualCustomerById.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult.getStatus());
    }

    /**
     * Method under test: {@link CustomerQueryServiceImpl#getCustomerById(Integer)}
     */
    @Test
    void testGetCustomerById3() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch customer with ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch customer with ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch customer with ID: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch customer with ID: {}");
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
        owner2.setName("Customer found: {} - {}");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Customer found: {} - {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Customer found: {} - {}");

        Customer customer2 = new Customer();
        customer2.setEmail("john.smith@example.org");
        customer2.setId(2);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Customer found: {} - {}");
        customer2.setPhoneNumber("8605550118");

        Lease lease2 = new Lease();
        lease2.setCar(car2);
        lease2.setCustomer(customer2);
        lease2.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setId(2);
        lease2.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setStatus(LeaseStatus.ENDED);

        ArrayList<Lease> leases = new ArrayList<>();
        leases.add(lease2);
        leases.add(lease);

        Customer customer3 = new Customer();
        customer3.setEmail("jane.doe@example.org");
        customer3.setId(1);
        customer3.setLeases(leases);
        customer3.setName("Name");
        customer3.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer3);
        when(customerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        CustomerDto actualCustomerById = customerQueryServiceImpl.getCustomerById(1);

        // Assert
        verify(customerQueryRepository).findById(eq(1));
        List<LeaseDto> leaseDtoList = actualCustomerById.getLeaseDtoList();
        assertEquals(2, leaseDtoList.size());
        LeaseDto getResult = leaseDtoList.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        LeaseDto getResult2 = leaseDtoList.get(1);
        assertEquals("1970-01-01", getResult2.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult2.getStartDate().toLocalDate().toString());
        assertEquals("6625550144", actualCustomerById.getPhoneNumber());
        CarDto carDto = getResult.getCarDto();
        assertEquals("Customer found: {} - {}", carDto.getModel());
        assertEquals("Customer found: {} - {}", carDto.getVariant());
        assertEquals("Name", actualCustomerById.getName());
        CarDto carDto2 = getResult2.getCarDto();
        assertEquals("Received request to fetch customer with ID: {}", carDto2.getModel());
        assertEquals("Received request to fetch customer with ID: {}", carDto2.getVariant());
        assertEquals("jane.doe@example.org", actualCustomerById.getEmail());
        assertEquals(1, carDto2.getId().intValue());
        assertEquals(1, actualCustomerById.getId().intValue());
        assertEquals(1, getResult2.getId().intValue());
        assertEquals(2, carDto.getId().intValue());
        assertEquals(2, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult2.getStatus());
        assertEquals(LeaseStatus.ENDED, getResult.getStatus());
    }

    /**
     * Method under test: {@link CustomerQueryServiceImpl#getCustomerById(Integer)}
     */
    @Test
    void testGetCustomerById4() {
        // Arrange
        when(customerQueryRepository.findById(Mockito.<Integer>any()))
                .thenThrow(ErrorException.unauthorized("An error occurred"));

        // Act and Assert
        assertThrows(ErrorException.class, () -> customerQueryServiceImpl.getCustomerById(1));
        verify(customerQueryRepository).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link CustomerQueryServiceImpl#getLeaseHistoryByCustomerId(Integer)}
     */
    @Test
    void testGetLeaseHistoryByCustomerId() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        List<LeaseDto> actualLeaseHistoryByCustomerId = customerQueryServiceImpl.getLeaseHistoryByCustomerId(1);

        // Assert
        verify(customerQueryRepository).findById(eq(1));
        assertTrue(actualLeaseHistoryByCustomerId.isEmpty());
    }

    /**
     * Method under test:
     * {@link CustomerQueryServiceImpl#getLeaseHistoryByCustomerId(Integer)}
     */
    @Test
    void testGetLeaseHistoryByCustomerId2() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch lease history for customer with ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch lease history for customer with ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch lease history for customer with ID: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch lease history for customer with ID: {}");
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

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1);
        customer2.setLeases(leases);
        customer2.setName("Name");
        customer2.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer2);
        when(customerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        List<LeaseDto> actualLeaseHistoryByCustomerId = customerQueryServiceImpl.getLeaseHistoryByCustomerId(1);

        // Assert
        verify(customerQueryRepository).findById(eq(1));
        assertEquals(1, actualLeaseHistoryByCustomerId.size());
        LeaseDto getResult = actualLeaseHistoryByCustomerId.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        CarDto carDto = getResult.getCarDto();
        assertEquals("Received request to fetch lease history for customer with ID: {}", carDto.getModel());
        assertEquals("Received request to fetch lease history for customer with ID: {}", carDto.getVariant());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult.getStatus());
    }

    /**
     * Method under test:
     * {@link CustomerQueryServiceImpl#getLeaseHistoryByCustomerId(Integer)}
     */
    @Test
    void testGetLeaseHistoryByCustomerId3() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch lease history for customer with ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch lease history for customer with ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch lease history for customer with ID: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch lease history for customer with ID: {}");
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
        owner2.setName("Customer found: {} - {}");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Customer found: {} - {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Customer found: {} - {}");

        Customer customer2 = new Customer();
        customer2.setEmail("john.smith@example.org");
        customer2.setId(2);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Customer found: {} - {}");
        customer2.setPhoneNumber("8605550118");

        Lease lease2 = new Lease();
        lease2.setCar(car2);
        lease2.setCustomer(customer2);
        lease2.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setId(2);
        lease2.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setStatus(LeaseStatus.ENDED);

        ArrayList<Lease> leases = new ArrayList<>();
        leases.add(lease2);
        leases.add(lease);

        Customer customer3 = new Customer();
        customer3.setEmail("jane.doe@example.org");
        customer3.setId(1);
        customer3.setLeases(leases);
        customer3.setName("Name");
        customer3.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer3);
        when(customerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        List<LeaseDto> actualLeaseHistoryByCustomerId = customerQueryServiceImpl.getLeaseHistoryByCustomerId(1);

        // Assert
        verify(customerQueryRepository).findById(eq(1));
        assertEquals(2, actualLeaseHistoryByCustomerId.size());
        LeaseDto getResult = actualLeaseHistoryByCustomerId.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        LeaseDto getResult2 = actualLeaseHistoryByCustomerId.get(1);
        assertEquals("1970-01-01", getResult2.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult2.getStartDate().toLocalDate().toString());
        CarDto carDto = getResult.getCarDto();
        assertEquals("Customer found: {} - {}", carDto.getModel());
        assertEquals("Customer found: {} - {}", carDto.getVariant());
        CarDto carDto2 = getResult2.getCarDto();
        assertEquals("Received request to fetch lease history for customer with ID: {}", carDto2.getModel());
        assertEquals("Received request to fetch lease history for customer with ID: {}", carDto2.getVariant());
        assertEquals(1, carDto2.getId().intValue());
        assertEquals(1, getResult2.getId().intValue());
        assertEquals(2, carDto.getId().intValue());
        assertEquals(2, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult2.getStatus());
        assertEquals(LeaseStatus.ENDED, getResult.getStatus());
    }

    /**
     * Method under test:
     * {@link CustomerQueryServiceImpl#getLeaseHistoryByCustomerId(Integer)}
     */
    @Test
    void testGetLeaseHistoryByCustomerId4() {
        // Arrange
        when(customerQueryRepository.findById(Mockito.<Integer>any()))
                .thenThrow(ErrorException.unauthorized("An error occurred"));

        // Act and Assert
        assertThrows(ErrorException.class, () -> customerQueryServiceImpl.getLeaseHistoryByCustomerId(1));
        verify(customerQueryRepository).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link CustomerQueryServiceImpl#viewCarsForLease(CarStatus)}
     */
    @Test
    void testViewCarsForLease() {
        // Arrange
        when(carQueryRepository.findByStatus(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<CarResponseDto> actualViewCarsForLeaseResult = customerQueryServiceImpl.viewCarsForLease(CarStatus.IDLE);

        // Assert
        verify(carQueryRepository).findByStatus(eq("IDLE"));
        assertTrue(actualViewCarsForLeaseResult.isEmpty());
    }

    /**
     * Method under test:
     * {@link CustomerQueryServiceImpl#viewCarsForLease(CarStatus)}
     */
    @Test
    void testViewCarsForLease3() {
        // Arrange
        when(carQueryRepository.findByStatus(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<CarResponseDto> actualViewCarsForLeaseResult = customerQueryServiceImpl.viewCarsForLease(CarStatus.ON_LEASE);

        // Assert
        verify(carQueryRepository).findByStatus(eq("ON_LEASE"));
        assertTrue(actualViewCarsForLeaseResult.isEmpty());
    }

    /**
     * Method under test:
     * {@link CustomerQueryServiceImpl#viewCarsForLease(CarStatus)}
     */
    @Test
    void testViewCarsForLease4() {
        // Arrange
        when(carQueryRepository.findByStatus(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<CarResponseDto> actualViewCarsForLeaseResult = customerQueryServiceImpl.viewCarsForLease(CarStatus.ON_SERVICE);

        // Assert
        verify(carQueryRepository).findByStatus(eq("ON_SERVICE"));
        assertTrue(actualViewCarsForLeaseResult.isEmpty());
    }

    /**
     * Method under test: {@link CustomerQueryServiceImpl#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers() {
        // Arrange
        when(customerQueryRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<CustomerDto> actualAllCustomers = customerQueryServiceImpl.getAllCustomers();

        // Assert
        verify(customerQueryRepository).findAll();
        assertTrue(actualAllCustomers.isEmpty());
    }

    /**
     * Method under test: {@link CustomerQueryServiceImpl#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers2() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        ArrayList<Lease> leases = new ArrayList<>();
        customer.setLeases(leases);
        customer.setName("Fetching all customers from the database.");
        customer.setPhoneNumber("6625550144");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerQueryRepository.findAll()).thenReturn(customerList);

        // Act
        List<CustomerDto> actualAllCustomers = customerQueryServiceImpl.getAllCustomers();

        // Assert
        verify(customerQueryRepository).findAll();
        assertEquals(1, actualAllCustomers.size());
        CustomerDto getResult = actualAllCustomers.get(0);
        assertEquals("6625550144", getResult.getPhoneNumber());
        assertEquals("Fetching all customers from the database.", getResult.getName());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(leases, getResult.getLeaseDtoList());
    }

    /**
     * Method under test: {@link CustomerQueryServiceImpl#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers3() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Fetching all customers from the database.");
        customer.setPhoneNumber("6625550144");

        Customer customer2 = new Customer();
        customer2.setEmail("john.smith@example.org");
        customer2.setId(2);
        ArrayList<Lease> leases = new ArrayList<>();
        customer2.setLeases(leases);
        customer2.setName("Successfully fetched {} customers.");
        customer2.setPhoneNumber("8605550118");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer2);
        customerList.add(customer);
        when(customerQueryRepository.findAll()).thenReturn(customerList);

        // Act
        List<CustomerDto> actualAllCustomers = customerQueryServiceImpl.getAllCustomers();

        // Assert
        verify(customerQueryRepository).findAll();
        assertEquals(2, actualAllCustomers.size());
        CustomerDto getResult = actualAllCustomers.get(1);
        assertEquals("6625550144", getResult.getPhoneNumber());
        CustomerDto getResult2 = actualAllCustomers.get(0);
        assertEquals("8605550118", getResult2.getPhoneNumber());
        assertEquals("Fetching all customers from the database.", getResult.getName());
        assertEquals("Successfully fetched {} customers.", getResult2.getName());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals("john.smith@example.org", getResult2.getEmail());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(2, getResult2.getId().intValue());
        assertEquals(leases, getResult2.getLeaseDtoList());
        assertEquals(leases, getResult.getLeaseDtoList());
    }

    /**
     * Method under test: {@link CustomerQueryServiceImpl#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers4() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Fetching all customers from the database.");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Fetching all customers from the database.");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Fetching all customers from the database.");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Fetching all customers from the database.");
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

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1);
        customer2.setLeases(leases);
        customer2.setName("Fetching all customers from the database.");
        customer2.setPhoneNumber("6625550144");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer2);
        when(customerQueryRepository.findAll()).thenReturn(customerList);

        // Act
        List<CustomerDto> actualAllCustomers = customerQueryServiceImpl.getAllCustomers();

        // Assert
        verify(customerQueryRepository).findAll();
        assertEquals(1, actualAllCustomers.size());
        CustomerDto getResult = actualAllCustomers.get(0);
        List<LeaseDto> leaseDtoList = getResult.getLeaseDtoList();
        assertEquals(1, leaseDtoList.size());
        LeaseDto getResult2 = leaseDtoList.get(0);
        assertEquals("1970-01-01", getResult2.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult2.getStartDate().toLocalDate().toString());
        assertEquals("6625550144", getResult.getPhoneNumber());
        CarDto carDto = getResult2.getCarDto();
        assertEquals("Fetching all customers from the database.", carDto.getModel());
        assertEquals("Fetching all customers from the database.", carDto.getVariant());
        assertEquals("Fetching all customers from the database.", getResult.getName());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(1, getResult2.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult2.getStatus());
    }

    /**
     * Method under test: {@link CustomerQueryServiceImpl#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers5() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Fetching all customers from the database.");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Fetching all customers from the database.");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Fetching all customers from the database.");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Fetching all customers from the database.");
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
        owner2.setName("Successfully fetched {} customers.");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Successfully fetched {} customers.");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Successfully fetched {} customers.");

        Customer customer2 = new Customer();
        customer2.setEmail("john.smith@example.org");
        customer2.setId(2);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Successfully fetched {} customers.");
        customer2.setPhoneNumber("8605550118");

        Lease lease2 = new Lease();
        lease2.setCar(car2);
        lease2.setCustomer(customer2);
        lease2.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setId(2);
        lease2.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setStatus(LeaseStatus.ENDED);

        ArrayList<Lease> leases = new ArrayList<>();
        leases.add(lease2);
        leases.add(lease);

        Customer customer3 = new Customer();
        customer3.setEmail("jane.doe@example.org");
        customer3.setId(1);
        customer3.setLeases(leases);
        customer3.setName("Fetching all customers from the database.");
        customer3.setPhoneNumber("6625550144");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer3);
        when(customerQueryRepository.findAll()).thenReturn(customerList);

        // Act
        List<CustomerDto> actualAllCustomers = customerQueryServiceImpl.getAllCustomers();

        // Assert
        verify(customerQueryRepository).findAll();
        assertEquals(1, actualAllCustomers.size());
        CustomerDto getResult = actualAllCustomers.get(0);
        List<LeaseDto> leaseDtoList = getResult.getLeaseDtoList();
        assertEquals(2, leaseDtoList.size());
        LeaseDto getResult2 = leaseDtoList.get(0);
        assertEquals("1970-01-01", getResult2.getEndDate().toLocalDate().toString());
        LeaseDto getResult3 = leaseDtoList.get(1);
        assertEquals("1970-01-01", getResult3.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult2.getStartDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult3.getStartDate().toLocalDate().toString());
        assertEquals("6625550144", getResult.getPhoneNumber());
        CarDto carDto = getResult3.getCarDto();
        assertEquals("Fetching all customers from the database.", carDto.getModel());
        assertEquals("Fetching all customers from the database.", carDto.getVariant());
        assertEquals("Fetching all customers from the database.", getResult.getName());
        CarDto carDto2 = getResult2.getCarDto();
        assertEquals("Successfully fetched {} customers.", carDto2.getModel());
        assertEquals("Successfully fetched {} customers.", carDto2.getVariant());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(1, getResult3.getId().intValue());
        assertEquals(2, carDto2.getId().intValue());
        assertEquals(2, getResult2.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult3.getStatus());
        assertEquals(LeaseStatus.ENDED, getResult2.getStatus());
    }
}
