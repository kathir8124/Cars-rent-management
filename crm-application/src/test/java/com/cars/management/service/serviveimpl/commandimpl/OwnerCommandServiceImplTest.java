package com.cars.management.service.serviveimpl.commandimpl;

import com.cars.management.dto.request.CarDto;
import com.cars.management.dto.request.CreateOwnerRequestDto;
import com.cars.management.dto.request.RegisterCarRequestDto;
import com.cars.management.dto.request.UpdateOwnerDetailsRequestDto;
import com.cars.management.dto.response.RegisterCarResponseDto;
import com.cars.management.dto.response.UpdateOwnerDetailsResponseDto;
import com.cars.management.entity.Car;
import com.cars.management.entity.Owner;
import com.cars.management.enums.CarStatus;
import com.cars.management.exception.ErrorException;
import com.cars.management.repository.commandrepository.CarCommandRepository;
import com.cars.management.repository.commandrepository.OwnerCommandRepository;
import com.cars.management.repository.queryrepository.OwnerQueryRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {OwnerCommandServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class OwnerCommandServiceImplTest {
    @MockBean
    private CarCommandRepository carCommandRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private OwnerCommandRepository ownerCommandRepository;

    @Autowired
    private OwnerCommandServiceImpl ownerCommandServiceImpl;

    @MockBean
    private OwnerQueryRepository ownerQueryRepository;

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#createOwner(CreateOwnerRequestDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateOwner() {
        // Arrange
        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        when(modelMapper.map(Mockito.any(), Mockito.<Class<Owner>>any())).thenReturn(owner);

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1);
        owner2.setName("Name");
        owner2.setPhoneNumber("6625550144");
        when(ownerCommandRepository.save(Mockito.<Owner>any())).thenReturn(owner2);

        // Act
        ownerCommandServiceImpl.createOwner(new CreateOwnerRequestDto("Name", "jane.doe@example.org", "6625550144"));
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#registerCar(RegisterCarRequestDto)}
     */
    @Test
    void testRegisterCar() {
        // Arrange
        ArrayList<Car> carList = new ArrayList<>();
        when(carCommandRepository.saveAll(Mockito.<Iterable<Car>>any())).thenReturn(carList);

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        RegisterCarResponseDto actualRegisterCarResult = ownerCommandServiceImpl
                .registerCar(new RegisterCarRequestDto(1, new ArrayList<>()));

        // Assert
        verify(ownerQueryRepository).findById(eq(1));
        verify(carCommandRepository).saveAll(isA(Iterable.class));
        assertEquals("Name", actualRegisterCarResult.getName());
        assertEquals(1, actualRegisterCarResult.getId().intValue());
        assertEquals(carList, actualRegisterCarResult.getCar());
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#registerCar(RegisterCarRequestDto)}
     */
    @Test
    void testRegisterCar2() {
        // Arrange
        when(carCommandRepository.saveAll(Mockito.<Iterable<Car>>any())).thenReturn(new ArrayList<>());

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        ArrayList<CarDto> carDtoList = new ArrayList<>();
        CarDto buildResult = CarDto.builder().id(1).model("Model").variant("Variant").build();
        carDtoList.add(buildResult);

        // Act
        RegisterCarResponseDto actualRegisterCarResult = ownerCommandServiceImpl
                .registerCar(new RegisterCarRequestDto(1, carDtoList));

        // Assert
        verify(ownerQueryRepository).findById(eq(1));
        verify(carCommandRepository).saveAll(isA(Iterable.class));
        List<CarDto> car = actualRegisterCarResult.getCar();
        assertEquals(1, car.size());
        CarDto getResult = car.get(0);
        assertEquals("Model", getResult.getModel());
        assertEquals("Name", actualRegisterCarResult.getName());
        assertEquals("Variant", getResult.getVariant());
        assertNull(getResult.getId());
        assertEquals(1, actualRegisterCarResult.getId().intValue());
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#registerCar(RegisterCarRequestDto)}
     */
    @Test
    void testRegisterCar3() {
        // Arrange
        when(carCommandRepository.saveAll(Mockito.<Iterable<Car>>any())).thenReturn(new ArrayList<>());

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        ArrayList<CarDto> carDtoList = new ArrayList<>();
        CarDto buildResult = CarDto.builder().id(1).model("Model").variant("Variant").build();
        carDtoList.add(buildResult);
        CarDto buildResult2 = CarDto.builder().id(1).model("Model").variant("Variant").build();
        carDtoList.add(buildResult2);

        // Act
        RegisterCarResponseDto actualRegisterCarResult = ownerCommandServiceImpl
                .registerCar(new RegisterCarRequestDto(1, carDtoList));

        // Assert
        verify(ownerQueryRepository).findById(eq(1));
        verify(carCommandRepository).saveAll(isA(Iterable.class));
        List<CarDto> car = actualRegisterCarResult.getCar();
        assertEquals(2, car.size());
        CarDto getResult = car.get(0);
        assertEquals("Model", getResult.getModel());
        CarDto getResult2 = car.get(1);
        assertEquals("Model", getResult2.getModel());
        assertEquals("Name", actualRegisterCarResult.getName());
        assertEquals("Variant", getResult.getVariant());
        assertEquals("Variant", getResult2.getVariant());
        assertNull(getResult.getId());
        assertNull(getResult2.getId());
        assertEquals(1, actualRegisterCarResult.getId().intValue());
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#registerCar(RegisterCarRequestDto)}
     */
    @Test
    void testRegisterCar4() {
        // Arrange
        when(carCommandRepository.saveAll(Mockito.<Iterable<Car>>any()))
                .thenThrow(ErrorException.unauthorized("An error occurred"));

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ErrorException.class,
                () -> ownerCommandServiceImpl.registerCar(new RegisterCarRequestDto(1, new ArrayList<>())));
        verify(ownerQueryRepository).findById(eq(1));
        verify(carCommandRepository).saveAll(isA(Iterable.class));
    }

    /**
     * Method under test: {@link OwnerCommandServiceImpl#deleteOwnerById(Integer)}
     */
    @Test
    void testDeleteOwnerById() {
        // Arrange
        doNothing().when(ownerCommandRepository).deleteById(Mockito.<Integer>any());

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        ownerCommandServiceImpl.deleteOwnerById(1);

        // Assert that nothing has changed
        verify(ownerCommandRepository).deleteById(eq(1));
        verify(ownerQueryRepository).findById(eq(1));
    }

    /**
     * Method under test: {@link OwnerCommandServiceImpl#deleteOwnerById(Integer)}
     */
    @Test
    void testDeleteOwnerById2() {
        // Arrange
        doThrow(ErrorException.unauthorized("An error occurred")).when(ownerCommandRepository)
                .deleteById(Mockito.<Integer>any());

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ErrorException.class, () -> ownerCommandServiceImpl.deleteOwnerById(1));
        verify(ownerCommandRepository).deleteById(eq(1));
        verify(ownerQueryRepository).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#updateOwnerById(UpdateOwnerDetailsRequestDto)}
     */
    @Test
    void testUpdateOwnerById() {
        // Arrange
        UpdateOwnerDetailsResponseDto buildResult = UpdateOwnerDetailsResponseDto.builder()
                .email("jane.doe@example.org")
                .id(1)
                .name("Name")
                .phoneNumber("6625550144")
                .build();
        when(modelMapper.map(Mockito.any(), Mockito.<Class<UpdateOwnerDetailsResponseDto>>any()))
                .thenReturn(buildResult);

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        when(ownerCommandRepository.save(Mockito.<Owner>any())).thenReturn(owner);

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1);
        owner2.setName("Name");
        owner2.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner2);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        ownerCommandServiceImpl.updateOwnerById(new UpdateOwnerDetailsRequestDto());

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(ownerQueryRepository).findById(isNull());
        verify(ownerCommandRepository).save(isA(Owner.class));
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#updateOwnerById(UpdateOwnerDetailsRequestDto)}
     */
    @Test
    void testUpdateOwnerById2() {
        // Arrange
        when(modelMapper.map(Mockito.any(), Mockito.<Class<UpdateOwnerDetailsResponseDto>>any()))
                .thenThrow(ErrorException.unauthorized("An error occurred"));

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        when(ownerCommandRepository.save(Mockito.<Owner>any())).thenReturn(owner);

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1);
        owner2.setName("Name");
        owner2.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner2);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ErrorException.class,
                () -> ownerCommandServiceImpl.updateOwnerById(new UpdateOwnerDetailsRequestDto()));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(ownerQueryRepository).findById(isNull());
        verify(ownerCommandRepository).save(isA(Owner.class));
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#deleteCarByOwnerIdAndCarId(Integer, Integer)}
     */
    @Test
    void testDeleteCarByOwnerIdAndCarId() {
        // Arrange
        doNothing().when(carCommandRepository).deleteCarByCarId(Mockito.<Integer>any());

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to delete car for owner with ID: {} and car ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to delete car for owner with ID: {} and car ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to delete car for owner with ID: {} and car ID: {}");

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
        ownerCommandServiceImpl.deleteCarByOwnerIdAndCarId(1, 1);

        // Assert that nothing has changed
        verify(carCommandRepository).deleteCarByCarId(eq(1));
        verify(ownerQueryRepository).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#deleteCarByOwnerIdAndCarId(Integer, Integer)}
     */
    @Test
    void testDeleteCarByOwnerIdAndCarId2() {
        // Arrange
        doNothing().when(carCommandRepository).deleteCarByCarId(Mockito.<Integer>any());

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to delete car for owner with ID: {} and car ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to delete car for owner with ID: {} and car ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to delete car for owner with ID: {} and car ID: {}");

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2);
        owner2.setName("Successfully deleted car with ID: {} for owner with ID: {}");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Successfully deleted car with ID: {} for owner with ID: {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Successfully deleted car with ID: {} for owner with ID: {}");

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
        ownerCommandServiceImpl.deleteCarByOwnerIdAndCarId(1, 1);

        // Assert that nothing has changed
        verify(carCommandRepository).deleteCarByCarId(eq(1));
        verify(ownerQueryRepository).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#deleteCarByOwnerIdAndCarId(Integer, Integer)}
     */
    @Test
    void testDeleteCarByOwnerIdAndCarId3() {
        // Arrange
        doThrow(ErrorException.unauthorized("An error occurred")).when(carCommandRepository)
                .deleteCarByCarId(Mockito.<Integer>any());

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to delete car for owner with ID: {} and car ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to delete car for owner with ID: {} and car ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to delete car for owner with ID: {} and car ID: {}");

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

        // Act and Assert
        assertThrows(ErrorException.class, () -> ownerCommandServiceImpl.deleteCarByOwnerIdAndCarId(1, 1));
        verify(carCommandRepository).deleteCarByCarId(eq(1));
        verify(ownerQueryRepository).findById(eq(1));
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#updateCarDetails(RegisterCarRequestDto)}
     */
    @Test
    void testUpdateCarDetails() {
        // Arrange
        ArrayList<Car> carList = new ArrayList<>();
        when(carCommandRepository.saveAll(Mockito.<Iterable<Car>>any())).thenReturn(carList);

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        RegisterCarResponseDto actualUpdateCarDetailsResult = ownerCommandServiceImpl
                .updateCarDetails(new RegisterCarRequestDto(1, new ArrayList<>()));

        // Assert
        verify(ownerQueryRepository).findById(eq(1));
        verify(carCommandRepository).saveAll(isA(Iterable.class));
        assertEquals("Name", actualUpdateCarDetailsResult.getName());
        assertEquals(1, actualUpdateCarDetailsResult.getId().intValue());
        assertEquals(carList, actualUpdateCarDetailsResult.getCar());
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#updateCarDetails(RegisterCarRequestDto)}
     */
    @Test
    void testUpdateCarDetails2() {
        // Arrange
        when(carCommandRepository.saveAll(Mockito.<Iterable<Car>>any()))
                .thenThrow(ErrorException.unauthorized("An error occurred"));

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Name");
        owner.setPhoneNumber("6625550144");
        Optional<Owner> ofResult = Optional.of(owner);
        when(ownerQueryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ErrorException.class,
                () -> ownerCommandServiceImpl.updateCarDetails(new RegisterCarRequestDto(1, new ArrayList<>())));
        verify(ownerQueryRepository).findById(eq(1));
        verify(carCommandRepository).saveAll(isA(Iterable.class));
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#updateCarDetails(RegisterCarRequestDto)}
     */
    @Test
    void testUpdateCarDetails3() {
        // Arrange
        when(carCommandRepository.saveAll(Mockito.<Iterable<Car>>any())).thenReturn(new ArrayList<>());

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to update car details for owner with ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to update car details for owner with ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to update car details for owner with ID: {}");

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

        ArrayList<CarDto> carDtoList = new ArrayList<>();
        CarDto buildResult = CarDto.builder().id(1).model("Model").variant("Variant").build();
        carDtoList.add(buildResult);

        // Act
        RegisterCarResponseDto actualUpdateCarDetailsResult = ownerCommandServiceImpl
                .updateCarDetails(new RegisterCarRequestDto(1, carDtoList));

        // Assert
        verify(ownerQueryRepository).findById(eq(1));
        verify(carCommandRepository).saveAll(isA(Iterable.class));
        List<CarDto> car2 = actualUpdateCarDetailsResult.getCar();
        CarDto getResult = car2.get(0);
        assertEquals("Model", getResult.getModel());
        assertEquals("Name", actualUpdateCarDetailsResult.getName());
        assertEquals("Variant", getResult.getVariant());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(1, actualUpdateCarDetailsResult.getId().intValue());
        assertEquals(carDtoList, car2);
    }

    /**
     * Method under test:
     * {@link OwnerCommandServiceImpl#updateCarDetails(RegisterCarRequestDto)}
     */
    @Test
    void testUpdateCarDetails4() {
        // Arrange
        when(carCommandRepository.saveAll(Mockito.<Iterable<Car>>any())).thenReturn(new ArrayList<>());

        Owner owner = new Owner();
        owner.setCars(new ArrayList<>());
        owner.setEmail("jane.doe@example.org");
        owner.setId(1);
        owner.setName("Received request to update car details for owner with ID: {}");
        owner.setPhoneNumber("6625550144");

        Car car = new Car();
        car.setId(1);
        car.setLeases(new ArrayList<>());
        car.setModel("Received request to update car details for owner with ID: {}");
        car.setOwner(owner);
        car.setStatus(CarStatus.IDLE);
        car.setVariant("Received request to update car details for owner with ID: {}");

        Owner owner2 = new Owner();
        owner2.setCars(new ArrayList<>());
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2);
        owner2.setName("Successfully updated {} cars for owner with ID: {}");
        owner2.setPhoneNumber("8605550118");

        Car car2 = new Car();
        car2.setId(2);
        car2.setLeases(new ArrayList<>());
        car2.setModel("Successfully updated {} cars for owner with ID: {}");
        car2.setOwner(owner2);
        car2.setStatus(CarStatus.ON_LEASE);
        car2.setVariant("Successfully updated {} cars for owner with ID: {}");

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

        ArrayList<CarDto> carDtoList = new ArrayList<>();
        CarDto buildResult = CarDto.builder().id(1).model("Model").variant("Variant").build();
        carDtoList.add(buildResult);

        // Act
        RegisterCarResponseDto actualUpdateCarDetailsResult = ownerCommandServiceImpl
                .updateCarDetails(new RegisterCarRequestDto(1, carDtoList));

        // Assert
        verify(ownerQueryRepository).findById(eq(1));
        verify(carCommandRepository).saveAll(isA(Iterable.class));
        List<CarDto> car3 = actualUpdateCarDetailsResult.getCar();
        CarDto getResult = car3.get(0);
        assertEquals("Model", getResult.getModel());
        assertEquals("Name", actualUpdateCarDetailsResult.getName());
        assertEquals("Variant", getResult.getVariant());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(1, actualUpdateCarDetailsResult.getId().intValue());
        assertEquals(carDtoList, car3);
    }
}
