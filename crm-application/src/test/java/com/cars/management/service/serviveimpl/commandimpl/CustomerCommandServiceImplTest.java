package com.cars.management.service.serviveimpl.commandimpl;

import com.cars.management.dto.request.CarDto;
import com.cars.management.dto.request.CreateCustomerRequestDto;
import com.cars.management.dto.request.LeaseDto;
import com.cars.management.dto.request.UpdateCustomerDetailsRequestDto;
import com.cars.management.dto.response.CustomerStartLeaseResponseDto;
import com.cars.management.dto.response.UpdateCustomerDetailsResponseDto;
import com.cars.management.entity.Car;
import com.cars.management.entity.Customer;
import com.cars.management.entity.Lease;
import com.cars.management.entity.Owner;
import com.cars.management.enums.CarStatus;
import com.cars.management.enums.LeaseStatus;
import com.cars.management.exception.ErrorException;
import com.cars.management.repository.commandrepository.CarCommandRepository;
import com.cars.management.repository.commandrepository.CustomerCommandRepository;
import com.cars.management.repository.commandrepository.LeaseCommandRepository;
import com.cars.management.repository.queryrepository.CarQueryRepository;
import com.cars.management.repository.queryrepository.CustomerQueryRepository;
import com.cars.management.repository.queryrepository.LeaseQueryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomerCommandServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CustomerCommandServiceImplTest {
    @MockBean
    private CarCommandRepository carCommandRepository;

    @MockBean
    private CarQueryRepository carQueryRepository;

    @MockBean
    private CustomerCommandRepository customerCommandRepository;

    @Autowired
    private CustomerCommandServiceImpl customerCommandServiceImpl;

    @MockBean
    private CustomerQueryRepository customerQueryRepository;

    @MockBean
    private LeaseCommandRepository leaseCommandRepository;

    @MockBean
    private LeaseQueryRepository leaseQueryRepository;

    @MockBean
    private ModelMapper modelMapper;

    /**
     * Method under test:
     * {@link CustomerCommandServiceImpl#registerCustomer(CreateCustomerRequestDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegisterCustomer() {
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");
        when(modelMapper.map(Mockito.any(), Mockito.<Class<Customer>>any())).thenReturn(customer);

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Name");
        customer2.setPhoneNumber("6625550144");
        when(customerCommandRepository.save(Mockito.<Customer>any())).thenReturn(customer2);

        customerCommandServiceImpl
                .registerCustomer(new CreateCustomerRequestDto("Name", "jane.doe@example.org", "6625550144"));
    }

    /**
     * Method under test:
     * {@link CustomerCommandServiceImpl#deleteCustomerById(Integer)}
     */
    @Test
    void testDeleteCustomerById() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        doNothing().when(customerCommandRepository).deleteById(Mockito.<Integer>any());

        // Act
        customerCommandServiceImpl.deleteCustomerById(1);

        // Assert that nothing has changed
        verify(customerCommandRepository).deleteById(eq(1));
        verify(customerQueryRepository).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link CustomerCommandServiceImpl#deleteCustomerById(Integer)}
     */
    @Test
    void testDeleteCustomerById2() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        doThrow(ErrorException.unauthorized("An error occurred")).when(customerCommandRepository)
                .deleteById(Mockito.<Integer>any());

        // Act and Assert
        assertThrows(ErrorException.class, () -> customerCommandServiceImpl.deleteCustomerById(1));
        verify(customerCommandRepository).deleteById(eq(1));
        verify(customerQueryRepository).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link CustomerCommandServiceImpl#updateCustomerById(UpdateCustomerDetailsRequestDto)}
     */
    @Test
    void testUpdateCustomerById() {
        // Arrange
        UpdateCustomerDetailsResponseDto buildResult = UpdateCustomerDetailsResponseDto.builder()
                .email("jane.doe@example.org")
                .id(1)
                .name("Name")
                .phoneNumber("6625550144")
                .build();
        when(modelMapper.map(Mockito.any(), Mockito.<Class<UpdateCustomerDetailsResponseDto>>any()))
                .thenReturn(buildResult);

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Name");
        customer2.setPhoneNumber("6625550144");
        when(customerCommandRepository.save(Mockito.<Customer>any())).thenReturn(customer2);

        // Act
        customerCommandServiceImpl.updateCustomerById(new UpdateCustomerDetailsRequestDto());

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(customerQueryRepository).findById(isNull());
        verify(customerCommandRepository).save(isA(Customer.class));
    }

    /**
     * Method under test:
     * {@link CustomerCommandServiceImpl#updateCustomerById(UpdateCustomerDetailsRequestDto)}
     */
    @Test
    void testUpdateCustomerById2() {
        // Arrange
        when(modelMapper.map(Mockito.any(), Mockito.<Class<UpdateCustomerDetailsResponseDto>>any()))
                .thenThrow(ErrorException.unauthorized("An error occurred"));

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Name");
        customer2.setPhoneNumber("6625550144");
        when(customerCommandRepository.save(Mockito.<Customer>any())).thenReturn(customer2);

        // Act and Assert
        assertThrows(ErrorException.class,
                () -> customerCommandServiceImpl.updateCustomerById(new UpdateCustomerDetailsRequestDto()));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(customerQueryRepository).findById(isNull());
        verify(customerCommandRepository).save(isA(Customer.class));
    }

    /**
     * Method under test:
     * {@link CustomerCommandServiceImpl#startLease(Integer, Integer)}
     */
    @Test
    void testStartLease() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        ArrayList<Lease> leases = new ArrayList<>();
        customer.setLeases(leases);
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerCommandRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

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
        Optional<Car> ofResult2 = Optional.of(car);
        when(carQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult2);

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1);
        owner2.setName("Name");
        owner2.setPhoneNumber("6625550144");

        Car car2 = new Car();
        car2.setId(1);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Model");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.IDLE);
        car2.setVariant("Variant");
        when(carCommandRepository.save(Mockito.<Car>any())).thenReturn(car2);
        when(leaseQueryRepository.findByCustomerIdAndStatus(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        Owner owner3 = new Owner();
        owner3.setCars(new ArrayList<>());
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1);
        owner3.setName("Name");
        owner3.setPhoneNumber("6625550144");

        Car car3 = new Car();
        car3.setId(1);
        car3.setLeases(new ArrayList<>());
        car3.setModel("Model");
        car3.setOwner(owner3);
        car3.setStatus(CarStatus.IDLE);
        car3.setVariant("Variant");

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Name");
        customer2.setPhoneNumber("6625550144");

        Lease lease = new Lease();
        lease.setCar(car3);
        lease.setCustomer(customer2);
        lease.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setId(1);
        lease.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setStatus(LeaseStatus.ACTIVE);
        when(leaseCommandRepository.save(Mockito.<Lease>any())).thenReturn(lease);

        // Act
        CustomerStartLeaseResponseDto actualStartLeaseResult = customerCommandServiceImpl.startLease(1, 1);

        // Assert
        verify(leaseQueryRepository).findByCustomerIdAndStatus(eq(1), eq("ACTIVE"));
        verify(customerCommandRepository).findById(eq(1));
        verify(carQueryRepository).findById(eq(1));
        verify(carCommandRepository).save(isA(Car.class));
        verify(leaseCommandRepository).save(isA(Lease.class));
        assertEquals("Name", actualStartLeaseResult.getName());
        assertEquals(1, actualStartLeaseResult.getId().intValue());
        assertEquals(leases, actualStartLeaseResult.getLeaseDtoList());
    }

    /**
     * Method under test:
     * {@link CustomerCommandServiceImpl#startLease(Integer, Integer)}
     */
    @Test
    void testStartLease2() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerCommandRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

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
        Optional<Car> ofResult2 = Optional.of(car);
        when(carQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult2);

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1);
        owner2.setName("Name");
        owner2.setPhoneNumber("6625550144");

        Car car2 = new Car();
        car2.setId(1);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Model");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.IDLE);
        car2.setVariant("Variant");
        when(carCommandRepository.save(Mockito.<Car>any())).thenReturn(car2);
        when(leaseQueryRepository.findByCustomerIdAndStatus(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(leaseCommandRepository.save(Mockito.<Lease>any())).thenThrow(ErrorException.unauthorized("An error occurred"));

        // Act and Assert
        assertThrows(ErrorException.class, () -> customerCommandServiceImpl.startLease(1, 1));
        verify(leaseQueryRepository).findByCustomerIdAndStatus(eq(1), eq("ACTIVE"));
        verify(customerCommandRepository).findById(eq(1));
        verify(carQueryRepository).findById(eq(1));
        verify(carCommandRepository).save(isA(Car.class));
        verify(leaseCommandRepository).save(isA(Lease.class));
    }

    /**
     * Method under test:
     * {@link CustomerCommandServiceImpl#startLease(Integer, Integer)}
     */
    @Test
    void testStartLease3() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to start lease for customer ID: {} and car ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to start lease for customer ID: {} and car ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to start lease for customer ID: {} and car ID: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to start lease for customer ID: {} and car ID: {}");
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
        when(customerCommandRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1);
        owner2.setName("Name");
        owner2.setPhoneNumber("6625550144");

        Car car2 = new Car();
        car2.setId(1);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Model");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.IDLE);
        car2.setVariant("Variant");
        Optional<Car> ofResult2 = Optional.of(car2);
        when(carQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult2);

        Owner owner3 = new Owner();
        owner3.setCars(new ArrayList<>());
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1);
        owner3.setName("Name");
        owner3.setPhoneNumber("6625550144");

        Car car3 = new Car();
        car3.setId(1);
        car3.setLeases(new ArrayList<>());
        car3.setModel("Model");
        car3.setOwner(owner3);
        car3.setStatus(CarStatus.IDLE);
        car3.setVariant("Variant");
        when(carCommandRepository.save(Mockito.<Car>any())).thenReturn(car3);
        when(leaseQueryRepository.findByCustomerIdAndStatus(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        Owner owner4 = new Owner();
        owner4.setCars(new ArrayList<>());
        owner4.setEmail("jane.doe@example.org");
        owner4.setId(1);
        owner4.setName("Name");
        owner4.setPhoneNumber("6625550144");

        Car car4 = new Car();
        car4.setId(1);
        car4.setLeases(new ArrayList<>());
        car4.setModel("Model");
        car4.setOwner(owner4);
        car4.setStatus(CarStatus.IDLE);
        car4.setVariant("Variant");

        Customer customer3 = new Customer();
        customer3.setEmail("jane.doe@example.org");
        customer3.setId(1);
        customer3.setLeases(new ArrayList<>());
        customer3.setName("Name");
        customer3.setPhoneNumber("6625550144");

        Lease lease2 = new Lease();
        lease2.setCar(car4);
        lease2.setCustomer(customer3);
        lease2.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setId(1);
        lease2.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setStatus(LeaseStatus.ACTIVE);
        when(leaseCommandRepository.save(Mockito.<Lease>any())).thenReturn(lease2);

        // Act
        CustomerStartLeaseResponseDto actualStartLeaseResult = customerCommandServiceImpl.startLease(1, 1);

        // Assert
        verify(leaseQueryRepository).findByCustomerIdAndStatus(eq(1), eq("ACTIVE"));
        verify(customerCommandRepository).findById(eq(1));
        verify(carQueryRepository).findById(eq(1));
        verify(carCommandRepository).save(isA(Car.class));
        verify(leaseCommandRepository).save(isA(Lease.class));
        List<LeaseDto> leaseDtoList = actualStartLeaseResult.getLeaseDtoList();
        assertEquals(1, leaseDtoList.size());
        LeaseDto getResult = leaseDtoList.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        assertEquals("Name", actualStartLeaseResult.getName());
        CarDto carDto = getResult.getCarDto();
        assertEquals("Received request to start lease for customer ID: {} and car ID: {}", carDto.getModel());
        assertEquals("Received request to start lease for customer ID: {} and car ID: {}", carDto.getVariant());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(1, actualStartLeaseResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult.getStatus());
    }

    /**
     * Method under test:
     * {@link CustomerCommandServiceImpl#startLease(Integer, Integer)}
     */
    @Test
    void testStartLease4() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to start lease for customer ID: {} and car ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to start lease for customer ID: {} and car ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to start lease for customer ID: {} and car ID: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to start lease for customer ID: {} and car ID: {}");
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
        owner2.setName("Successfully started lease for customer ID: {} and car ID: {}");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Successfully started lease for customer ID: {} and car ID: {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Successfully started lease for customer ID: {} and car ID: {}");

        Customer customer2 = new Customer();
        customer2.setEmail("john.smith@example.org");
        customer2.setId(2);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Successfully started lease for customer ID: {} and car ID: {}");
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
        when(customerCommandRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Owner owner3 = new Owner();
        owner3.setCars(new ArrayList<>());
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1);
        owner3.setName("Name");
        owner3.setPhoneNumber("6625550144");

        Car car3 = new Car();
        car3.setId(1);
        car3.setLeases(new ArrayList<>());
        car3.setModel("Model");
        car3.setOwner(owner3);
        car3.setStatus(CarStatus.IDLE);
        car3.setVariant("Variant");
        Optional<Car> ofResult2 = Optional.of(car3);
        when(carQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult2);

        Owner owner4 = new Owner();
        owner4.setCars(new ArrayList<>());
        owner4.setEmail("jane.doe@example.org");
        owner4.setId(1);
        owner4.setName("Name");
        owner4.setPhoneNumber("6625550144");

        Car car4 = new Car();
        car4.setId(1);
        car4.setLeases(new ArrayList<>());
        car4.setModel("Model");
        car4.setOwner(owner4);
        car4.setStatus(CarStatus.IDLE);
        car4.setVariant("Variant");
        when(carCommandRepository.save(Mockito.<Car>any())).thenReturn(car4);
        when(leaseQueryRepository.findByCustomerIdAndStatus(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        Owner owner5 = new Owner();
        owner5.setCars(new ArrayList<>());
        owner5.setEmail("jane.doe@example.org");
        owner5.setId(1);
        owner5.setName("Name");
        owner5.setPhoneNumber("6625550144");

        Car car5 = new Car();
        car5.setId(1);
        car5.setLeases(new ArrayList<>());
        car5.setModel("Model");
        car5.setOwner(owner5);
        car5.setStatus(CarStatus.IDLE);
        car5.setVariant("Variant");

        Customer customer4 = new Customer();
        customer4.setEmail("jane.doe@example.org");
        customer4.setId(1);
        customer4.setLeases(new ArrayList<>());
        customer4.setName("Name");
        customer4.setPhoneNumber("6625550144");

        Lease lease3 = new Lease();
        lease3.setCar(car5);
        lease3.setCustomer(customer4);
        lease3.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease3.setId(1);
        lease3.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease3.setStatus(LeaseStatus.ACTIVE);
        when(leaseCommandRepository.save(Mockito.<Lease>any())).thenReturn(lease3);

        // Act
        CustomerStartLeaseResponseDto actualStartLeaseResult = customerCommandServiceImpl.startLease(1, 1);

        // Assert
        verify(leaseQueryRepository).findByCustomerIdAndStatus(eq(1), eq("ACTIVE"));
        verify(customerCommandRepository).findById(eq(1));
        verify(carQueryRepository).findById(eq(1));
        verify(carCommandRepository).save(isA(Car.class));
        verify(leaseCommandRepository).save(isA(Lease.class));
        List<LeaseDto> leaseDtoList = actualStartLeaseResult.getLeaseDtoList();
        assertEquals(2, leaseDtoList.size());
        LeaseDto getResult = leaseDtoList.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        LeaseDto getResult2 = leaseDtoList.get(1);
        assertEquals("1970-01-01", getResult2.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult2.getStartDate().toLocalDate().toString());
        assertEquals("Name", actualStartLeaseResult.getName());
        CarDto carDto = getResult2.getCarDto();
        assertEquals("Received request to start lease for customer ID: {} and car ID: {}", carDto.getModel());
        assertEquals("Received request to start lease for customer ID: {} and car ID: {}", carDto.getVariant());
        CarDto carDto2 = getResult.getCarDto();
        assertEquals("Successfully started lease for customer ID: {} and car ID: {}", carDto2.getModel());
        assertEquals("Successfully started lease for customer ID: {} and car ID: {}", carDto2.getVariant());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, getResult2.getId().intValue());
        assertEquals(1, actualStartLeaseResult.getId().intValue());
        assertEquals(2, carDto2.getId().intValue());
        assertEquals(2, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult2.getStatus());
        assertEquals(LeaseStatus.ENDED, getResult.getStatus());
    }

    /**
     * Method under test: {@link CustomerCommandServiceImpl#endLease(Integer)}
     */
    @Test
    void testEndLease() {
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
        when(carCommandRepository.save(Mockito.<Car>any())).thenReturn(car);

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1);
        owner2.setName("Name");
        owner2.setPhoneNumber("6625550144");

        Car car2 = new Car();
        car2.setId(1);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Model");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.IDLE);
        car2.setVariant("Variant");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");

        Lease lease = new Lease();
        lease.setCar(car2);
        lease.setCustomer(customer);
        lease.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setId(1);
        lease.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setStatus(LeaseStatus.ACTIVE);
        Optional<Lease> ofResult = Optional.of(lease);
        when(leaseQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Owner owner3 = new Owner();
        owner3.setCars(new ArrayList<>());
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1);
        owner3.setName("Name");
        owner3.setPhoneNumber("6625550144");

        Car car3 = new Car();
        car3.setId(1);
        car3.setLeases(new ArrayList<>());
        car3.setModel("Model");
        car3.setOwner(owner3);
        car3.setStatus(CarStatus.IDLE);
        car3.setVariant("Variant");

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Name");
        customer2.setPhoneNumber("6625550144");

        Lease lease2 = new Lease();
        lease2.setCar(car3);
        lease2.setCustomer(customer2);
        lease2.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setId(1);
        lease2.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease2.setStatus(LeaseStatus.ACTIVE);
        when(leaseCommandRepository.save(Mockito.<Lease>any())).thenReturn(lease2);

        // Act
        LeaseDto actualEndLeaseResult = customerCommandServiceImpl.endLease(1);

        // Assert
        verify(leaseQueryRepository).findById(eq(1));
        verify(carCommandRepository).save(isA(Car.class));
        verify(leaseCommandRepository).save(isA(Lease.class));
        assertEquals("1970-01-01", actualEndLeaseResult.getStartDate().toLocalDate().toString());
        CarDto carDto = actualEndLeaseResult.getCarDto();
        assertEquals("Model", carDto.getModel());
        assertEquals("Variant", carDto.getVariant());
        assertEquals(1, carDto.getId().intValue());
        assertEquals(1, actualEndLeaseResult.getId().intValue());
        assertEquals(LeaseStatus.ENDED, actualEndLeaseResult.getStatus());
    }

    /**
     * Method under test: {@link CustomerCommandServiceImpl#endLease(Integer)}
     */
    @Test
    void testEndLease2() {
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
        when(carCommandRepository.save(Mockito.<Car>any())).thenReturn(car);

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1);
        owner2.setName("Name");
        owner2.setPhoneNumber("6625550144");

        Car car2 = new Car();
        car2.setId(1);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Model");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.IDLE);
        car2.setVariant("Variant");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Name");
        customer.setPhoneNumber("6625550144");

        Lease lease = new Lease();
        lease.setCar(car2);
        lease.setCustomer(customer);
        lease.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setId(1);
        lease.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        lease.setStatus(LeaseStatus.ACTIVE);
        Optional<Lease> ofResult = Optional.of(lease);
        when(leaseQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        when(leaseCommandRepository.save(Mockito.<Lease>any())).thenThrow(ErrorException.unauthorized("An error occurred"));

        // Act and Assert
        assertThrows(ErrorException.class, () -> customerCommandServiceImpl.endLease(1));
        verify(leaseQueryRepository).findById(eq(1));
        verify(carCommandRepository).save(isA(Car.class));
        verify(leaseCommandRepository).save(isA(Lease.class));
    }
}
