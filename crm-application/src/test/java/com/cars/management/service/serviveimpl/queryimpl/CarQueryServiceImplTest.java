package com.cars.management.service.serviveimpl.queryimpl;

import com.cars.management.dto.response.CarResponseDto;
import com.cars.management.dto.response.LeaseResponseDto;
import com.cars.management.entity.Car;
import com.cars.management.entity.Customer;
import com.cars.management.entity.Lease;
import com.cars.management.entity.Owner;
import com.cars.management.enums.CarStatus;
import com.cars.management.enums.LeaseStatus;
import com.cars.management.exception.ErrorException;
import com.cars.management.repository.queryrepository.CarQueryRepository;
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

@ContextConfiguration(classes = {CarQueryServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CarQueryServiceImplTest {
    @MockBean
    private CarQueryRepository carQueryRepository;

    @Autowired
    private CarQueryServiceImpl carQueryServiceImpl;

    @MockBean
    private OwnerQueryRepository ownerQueryRepository;

    /**
     * Method under test:
     * {@link CarQueryServiceImpl#getCarStatusAndDetails(Integer)}
     */
    @Test
    void testGetCarStatusAndDetails() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        ArrayList<Lease> leases = new ArrayList<>();
        car.setLeases(leases);
        car.setModel("Model");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Variant");
        Optional<Car> ofResult = Optional.of(car);
        when(carQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        CarResponseDto actualCarStatusAndDetails = carQueryServiceImpl.getCarStatusAndDetails(1);

        // Assert
        verify(carQueryRepository).findById(eq(1));
        assertEquals("Model", actualCarStatusAndDetails.getModel());
        assertEquals("Variant", actualCarStatusAndDetails.getVariant());
        assertEquals(1, actualCarStatusAndDetails.getId().intValue());
        assertEquals(leases, actualCarStatusAndDetails.getLeaseDtoList());
    }

    /**
     * Method under test:
     * {@link CarQueryServiceImpl#getCarStatusAndDetails(Integer)}
     */
    @Test
    void testGetCarStatusAndDetails2() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch car details for car ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch car details for car ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch car details for car ID: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch car details for car ID: {}");
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
        owner2.setName("Name");
        owner2.setPhoneNumber("6625550144");

        Car car2 = new Car();
        car2.setId(1);
        car2.setLeases(leases);
        car2.setModel("Model");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.IDLE);
        car2.setVariant("Variant");
        Optional<Car> ofResult = Optional.of(car2);
        when(carQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        CarResponseDto actualCarStatusAndDetails = carQueryServiceImpl.getCarStatusAndDetails(1);

        // Assert
        verify(carQueryRepository).findById(eq(1));
        List<LeaseResponseDto> leaseDtoList = actualCarStatusAndDetails.getLeaseDtoList();
        assertEquals(1, leaseDtoList.size());
        LeaseResponseDto getResult = leaseDtoList.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        assertEquals("Model", actualCarStatusAndDetails.getModel());
        assertEquals("Variant", actualCarStatusAndDetails.getVariant());
        assertEquals(1, actualCarStatusAndDetails.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult.getStatus());
    }

    /**
     * Method under test:
     * {@link CarQueryServiceImpl#getCarStatusAndDetails(Integer)}
     */
    @Test
    void testGetCarStatusAndDetails3() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch car details for car ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch car details for car ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch car details for car ID: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch car details for car ID: {}");
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
        owner2.setName("Car details found for car ID: {}");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Car details found for car ID: {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Car details found for car ID: {}");

        Customer customer2 = new Customer();
        customer2.setEmail("john.smith@example.org");
        customer2.setId(2);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Car details found for car ID: {}");
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

        Owner owner3 = new Owner();
        owner3.setCars(new ArrayList<>());
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1);
        owner3.setName("Name");
        owner3.setPhoneNumber("6625550144");

        Car car3 = new Car();
        car3.setId(1);
        car3.setLeases(leases);
        car3.setModel("Model");
        car3.setOwner(owner3);
        car3.setStatus(CarStatus.IDLE);
        car3.setVariant("Variant");
        Optional<Car> ofResult = Optional.of(car3);
        when(carQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        CarResponseDto actualCarStatusAndDetails = carQueryServiceImpl.getCarStatusAndDetails(1);

        // Assert
        verify(carQueryRepository).findById(eq(1));
        List<LeaseResponseDto> leaseDtoList = actualCarStatusAndDetails.getLeaseDtoList();
        assertEquals(2, leaseDtoList.size());
        LeaseResponseDto getResult = leaseDtoList.get(0);
        assertEquals("1970-01-01", getResult.getEndDate().toLocalDate().toString());
        LeaseResponseDto getResult2 = leaseDtoList.get(1);
        assertEquals("1970-01-01", getResult2.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult.getStartDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult2.getStartDate().toLocalDate().toString());
        assertEquals("Model", actualCarStatusAndDetails.getModel());
        assertEquals("Variant", actualCarStatusAndDetails.getVariant());
        assertEquals(1, actualCarStatusAndDetails.getId().intValue());
        assertEquals(1, getResult2.getId().intValue());
        assertEquals(2, getResult.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult2.getStatus());
        assertEquals(LeaseStatus.ENDED, getResult.getStatus());
    }

    /**
     * Method under test:
     * {@link CarQueryServiceImpl#getCarStatusAndDetails(Integer)}
     */
    @Test
    void testGetCarStatusAndDetails4() {
        // Arrange
        when(carQueryRepository.findById(Mockito.<Integer>any()))
                .thenThrow(ErrorException.unauthorized("An error occurred"));

        // Act and Assert
        assertThrows(ErrorException.class, () -> carQueryServiceImpl.getCarStatusAndDetails(1));
        verify(carQueryRepository).findById(eq(1));
    }

    /**
     * Method under test: {@link CarQueryServiceImpl#getCarsByStatus(CarStatus)}
     */
    @Test
    void testGetCarsByStatus() {
        // Arrange
        when(carQueryRepository.findByStatus(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<CarResponseDto> actualCarsByStatus = carQueryServiceImpl.getCarsByStatus(CarStatus.IDLE);

        // Assert
        verify(carQueryRepository).findByStatus(eq("IDLE"));
        assertTrue(actualCarsByStatus.isEmpty());
    }

    /**
     * Method under test: {@link CarQueryServiceImpl#getCarsByStatus(CarStatus)}
     */
    @Test
    void testGetCarsByStatus2() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch cars with status: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        ArrayList<Lease> leases = new ArrayList<>();
        car.setLeases(leases);
        car.setModel("Received request to fetch cars with status: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch cars with status: {}");

        ArrayList<Car> carList = new ArrayList<>();
        carList.add(car);
        when(carQueryRepository.findByStatus(Mockito.<String>any())).thenReturn(carList);

        // Act
        List<CarResponseDto> actualCarsByStatus = carQueryServiceImpl.getCarsByStatus(CarStatus.IDLE);

        // Assert
        verify(carQueryRepository).findByStatus(eq("IDLE"));
        assertEquals(1, actualCarsByStatus.size());
        CarResponseDto getResult = actualCarsByStatus.get(0);
        assertEquals("Received request to fetch cars with status: {}", getResult.getModel());
        assertEquals("Received request to fetch cars with status: {}", getResult.getVariant());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(leases, getResult.getLeaseDtoList());
    }

    /**
     * Method under test: {@link CarQueryServiceImpl#getCarsByStatus(CarStatus)}
     */
    @Test
    void testGetCarsByStatus3() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch cars with status: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch cars with status: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch cars with status: {}");

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2);
        owner2.setName("Successfully fetched {} cars with status: {}");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        ArrayList<Lease> leases = new ArrayList<>();
        car2.setLeases(leases);
        car2.setModel("Successfully fetched {} cars with status: {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Successfully fetched {} cars with status: {}");

        ArrayList<Car> carList = new ArrayList<>();
        carList.add(car2);
        carList.add(car);
        when(carQueryRepository.findByStatus(Mockito.<String>any())).thenReturn(carList);

        // Act
        List<CarResponseDto> actualCarsByStatus = carQueryServiceImpl.getCarsByStatus(CarStatus.IDLE);

        // Assert
        verify(carQueryRepository).findByStatus(eq("IDLE"));
        assertEquals(2, actualCarsByStatus.size());
        CarResponseDto getResult = actualCarsByStatus.get(1);
        assertEquals("Received request to fetch cars with status: {}", getResult.getModel());
        assertEquals("Received request to fetch cars with status: {}", getResult.getVariant());
        CarResponseDto getResult2 = actualCarsByStatus.get(0);
        assertEquals("Successfully fetched {} cars with status: {}", getResult2.getModel());
        assertEquals("Successfully fetched {} cars with status: {}", getResult2.getVariant());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(2, getResult2.getId().intValue());
        assertEquals(leases, getResult2.getLeaseDtoList());
        assertEquals(leases, getResult.getLeaseDtoList());
    }

    /**
     * Method under test: {@link CarQueryServiceImpl#getCarsByStatus(CarStatus)}
     */
    @Test
    void testGetCarsByStatus4() {
        // Arrange
        when(carQueryRepository.findByStatus(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<CarResponseDto> actualCarsByStatus = carQueryServiceImpl.getCarsByStatus(CarStatus.ON_LEASE);

        // Assert
        verify(carQueryRepository).findByStatus(eq("ON_LEASE"));
        assertTrue(actualCarsByStatus.isEmpty());
    }

    /**
     * Method under test: {@link CarQueryServiceImpl#getCarsByStatus(CarStatus)}
     */
    @Test
    void testGetCarsByStatus5() {
        // Arrange
        when(carQueryRepository.findByStatus(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<CarResponseDto> actualCarsByStatus = carQueryServiceImpl.getCarsByStatus(CarStatus.ON_SERVICE);

        // Assert
        verify(carQueryRepository).findByStatus(eq("ON_SERVICE"));
        assertTrue(actualCarsByStatus.isEmpty());
    }

    /**
     * Method under test: {@link CarQueryServiceImpl#getCarsByStatus(CarStatus)}
     */
    @Test
    void testGetCarsByStatus6() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch cars with status: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch cars with status: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch cars with status: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch cars with status: {}");
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
        owner2.setName("Received request to fetch cars with status: {}");
        owner2.setPhoneNumber("6625550144");

        Car car2 = new Car();
        car2.setId(1);
        car2.setLeases(leases);
        car2.setModel("Received request to fetch cars with status: {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.IDLE);
        car2.setVariant("Received request to fetch cars with status: {}");

        ArrayList<Car> carList = new ArrayList<>();
        carList.add(car2);
        when(carQueryRepository.findByStatus(Mockito.<String>any())).thenReturn(carList);

        // Act
        List<CarResponseDto> actualCarsByStatus = carQueryServiceImpl.getCarsByStatus(CarStatus.IDLE);

        // Assert
        verify(carQueryRepository).findByStatus(eq("IDLE"));
        assertEquals(1, actualCarsByStatus.size());
        CarResponseDto getResult = actualCarsByStatus.get(0);
        List<LeaseResponseDto> leaseDtoList = getResult.getLeaseDtoList();
        assertEquals(1, leaseDtoList.size());
        LeaseResponseDto getResult2 = leaseDtoList.get(0);
        assertEquals("1970-01-01", getResult2.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult2.getStartDate().toLocalDate().toString());
        assertEquals("Received request to fetch cars with status: {}", getResult.getModel());
        assertEquals("Received request to fetch cars with status: {}", getResult.getVariant());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(1, getResult2.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult2.getStatus());
    }

    /**
     * Method under test: {@link CarQueryServiceImpl#getCarsByStatus(CarStatus)}
     */
    @Test
    void testGetCarsByStatus7() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to fetch cars with status: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to fetch cars with status: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to fetch cars with status: {}");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setLeases(new ArrayList<>());
        customer.setName("Received request to fetch cars with status: {}");
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
        owner2.setName("Successfully fetched {} cars with status: {}");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Successfully fetched {} cars with status: {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Successfully fetched {} cars with status: {}");

        Customer customer2 = new Customer();
        customer2.setEmail("john.smith@example.org");
        customer2.setId(2);
        customer2.setLeases(new ArrayList<>());
        customer2.setName("Successfully fetched {} cars with status: {}");
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

        Owner owner3 = new Owner();
        owner3.setCars(new ArrayList<>());
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1);
        owner3.setName("Received request to fetch cars with status: {}");
        owner3.setPhoneNumber("6625550144");

        Car car3 = new Car();
        car3.setId(1);
        car3.setLeases(leases);
        car3.setModel("Received request to fetch cars with status: {}");
        car3.setOwner(owner3);
        car3.setStatus(CarStatus.IDLE);
        car3.setVariant("Received request to fetch cars with status: {}");

        ArrayList<Car> carList = new ArrayList<>();
        carList.add(car3);
        when(carQueryRepository.findByStatus(Mockito.<String>any())).thenReturn(carList);

        // Act
        List<CarResponseDto> actualCarsByStatus = carQueryServiceImpl.getCarsByStatus(CarStatus.IDLE);

        // Assert
        verify(carQueryRepository).findByStatus(eq("IDLE"));
        assertEquals(1, actualCarsByStatus.size());
        CarResponseDto getResult = actualCarsByStatus.get(0);
        List<LeaseResponseDto> leaseDtoList = getResult.getLeaseDtoList();
        assertEquals(2, leaseDtoList.size());
        LeaseResponseDto getResult2 = leaseDtoList.get(0);
        assertEquals("1970-01-01", getResult2.getEndDate().toLocalDate().toString());
        LeaseResponseDto getResult3 = leaseDtoList.get(1);
        assertEquals("1970-01-01", getResult3.getEndDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult2.getStartDate().toLocalDate().toString());
        assertEquals("1970-01-01", getResult3.getStartDate().toLocalDate().toString());
        assertEquals("Received request to fetch cars with status: {}", getResult.getModel());
        assertEquals("Received request to fetch cars with status: {}", getResult.getVariant());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(1, getResult3.getId().intValue());
        assertEquals(2, getResult2.getId().intValue());
        assertEquals(LeaseStatus.ACTIVE, getResult3.getStatus());
        assertEquals(LeaseStatus.ENDED, getResult2.getStatus());
    }
}
