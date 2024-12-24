package com.cars.management.service.serviveimpl.commandimpl;

import com.cars.management.dto.request.CarDto;
import com.cars.management.dto.request.CreateOwnerRequestDto;
import com.cars.management.dto.request.RegisterCarRequestDto;
import com.cars.management.dto.request.UpdateOwnerDetailsRequestDto;
import com.cars.management.dto.response.CreateOwnerResponseDto;
import com.cars.management.dto.response.RegisterCarResponseDto;
import com.cars.management.dto.response.UpdateOwnerDetailsResponseDto;
import com.cars.management.entity.Car;
import com.cars.management.entity.Owner;
import com.cars.management.enums.CarStatus;
import com.cars.management.exception.ErrorException;
import com.cars.management.repository.commandrepository.CarCommandRepository;
import com.cars.management.repository.commandrepository.OwnerCommandRepository;
import com.cars.management.repository.queryrepository.OwnerQueryRepository;
import com.cars.management.service.serviveports.commandports.OwnerCommandService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerCommandServiceImpl implements OwnerCommandService{

     private final OwnerCommandRepository ownerCommandRepository;
     private final CarCommandRepository carCommandRepository;
     private final OwnerQueryRepository ownerQueryRepository;
     @Autowired
     private ModelMapper modelMapper;

    @Override
    @Transactional
    public CreateOwnerResponseDto createOwner(CreateOwnerRequestDto createOwnerRequestDto) {
        log.info("Starting owner creation process. Request: {}", createOwnerRequestDto);
        try {
            log.debug("Saving owner with details: {}", createOwnerRequestDto);
            Owner savedOwner = ownerCommandRepository.save(modelMapper.map(createOwnerRequestDto, Owner.class));
            log.info("Owner created successfully with ID: {}", savedOwner.getId());
            return modelMapper.map(savedOwner, CreateOwnerResponseDto.class);
        } catch (Exception e) {
            log.error("Error occurred while creating owner. Request Data: {}. Error: {}",
                    createOwnerRequestDto, e.getMessage(), e);
            throw ErrorException.internalError("Error while saving owner details: " + createOwnerRequestDto);
        }
    }

    @Override
    public RegisterCarResponseDto registerCar(RegisterCarRequestDto registerCarRequestDto) {
        log.info("Received request to register cars for owner with ID: {}", registerCarRequestDto.getOwnerId());
        try {
            Owner owner = ownerQueryRepository.findById(registerCarRequestDto.getOwnerId())
                    .orElseThrow(() -> ErrorException.resourceNotExist("Owner not found with ID: " + registerCarRequestDto.getOwnerId()));
            List<Car> carsToRegister = mapCarDtosToEntities(registerCarRequestDto.getCarDtoList(), owner);
            carCommandRepository.saveAll(carsToRegister);
            List<CarDto> carDtos = mapCarsToCarDtos(carsToRegister);
            log.info("Successfully registered {} cars for owner with ID: {}", carsToRegister.size(), owner.getId());
            return new RegisterCarResponseDto(owner.getId(), owner.getName(), carDtos);
        } catch (ErrorException e) {
            log.error("Error registering cars for owner with ID: {}. Exception: {}", registerCarRequestDto.getOwnerId(), e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error registering cars for owner with ID: {}. Exception: {}", registerCarRequestDto.getOwnerId(), e.getMessage(), e);
            throw ErrorException.internalError("Error while registering cars for owner ID: " + registerCarRequestDto.getOwnerId());
        }
    }

    @Override
    public void deleteOwnerById(Integer ownerId) {
        log.info("Received request to delete owner with ID: {}", ownerId);
        try {
            Owner owner = ownerQueryRepository.findById(ownerId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("Owner not found with ID: " + ownerId));
            log.debug("Owner found: {} - {}", owner.getId(), owner.getName());
            log.info("Deleting owner with ID: {}", ownerId);
            ownerCommandRepository.deleteById(ownerId);
            log.info("Successfully deleted owner with ID: {}", ownerId);
        } catch (ErrorException e) {
            log.error("Error deleting owner with ID: {}. Exception: {}", ownerId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while deleting owner with ID: {}. Exception: {}", ownerId, e.getMessage(), e);
            throw ErrorException.internalError("Unexpected error while deleting owner with ID: " + ownerId, e);
        }
    }

    @Override
    public UpdateOwnerDetailsResponseDto updateOwnerById(UpdateOwnerDetailsRequestDto updateOwnerDetailsRequestDto) {
        log.info("Received request to update owner with ID: {}", updateOwnerDetailsRequestDto.getId());
        try {
            Owner owner = ownerQueryRepository.findById(updateOwnerDetailsRequestDto.getId())
                    .orElseThrow(() -> ErrorException.resourceNotExist("Owner not found with ID: " + updateOwnerDetailsRequestDto.getId()));
            log.debug("Owner found: {} - {}", owner.getId(), owner.getName());
            updateOwnerDetails(owner, updateOwnerDetailsRequestDto);
            Owner savedOwner = ownerCommandRepository.save(owner);
            log.info("Successfully updated owner with ID: {}", savedOwner.getId());
            return modelMapper.map(savedOwner, UpdateOwnerDetailsResponseDto.class);
        } catch (ErrorException e) {
            log.error("Error updating owner with ID: {}. Exception: {}", updateOwnerDetailsRequestDto.getId(), e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating owner with ID: {}. Exception: {}", updateOwnerDetailsRequestDto.getId(), e.getMessage(), e);
            throw ErrorException.internalError("Unexpected error while updating owner with ID: " + updateOwnerDetailsRequestDto.getId(), e);
        }
    }

    @Override
    @Transactional
    public void deleteCarByOwnerIdAndCarId(Integer ownerId, Integer carId) {
        log.info("Received request to delete car for owner with ID: {} and car ID: {}", ownerId, carId);

        try {
            Owner owner = ownerQueryRepository.findById(ownerId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("Owner not found with ID: " + ownerId));
            Car carToDelete = getCarByOwnerAndId(owner, carId);
            System.out.println("----------------------> " + carToDelete.getId());
            carCommandRepository.deleteCarByCarId(carToDelete.getId());
            log.info("Successfully deleted car with ID: {} for owner with ID: {}", carId, ownerId);
        } catch (ErrorException e) {
            log.error("Error occurred while deleting car with ID: {} for owner with ID: {}. Exception: {}", carId, ownerId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while deleting car with ID: {} for owner with ID: {}. Exception: {}", carId, ownerId, e.getMessage(), e);
            throw ErrorException.internalError("Unexpected error while deleting car with ID: " + carId + " for owner ID: " + ownerId, e);
        }
    }



    @Override
    public RegisterCarResponseDto updateCarDetails(RegisterCarRequestDto registerCarRequestDto) {
        log.info("Received request to update car details for owner with ID: {}", registerCarRequestDto.getOwnerId());
        try {
            Owner owner = ownerQueryRepository.findById(registerCarRequestDto.getOwnerId())
                    .orElseThrow(() -> ErrorException.resourceNotExist("Owner not found with ID: " + registerCarRequestDto.getOwnerId()));
            List<Car> updatedCars = updateCars(owner, registerCarRequestDto.getCarDtoList());
            carCommandRepository.saveAll(updatedCars);
            log.info("Successfully updated {} cars for owner with ID: {}", updatedCars.size(), owner.getId());
            return buildResponse(owner, updatedCars);
        } catch (ErrorException e) {
            log.error("Error occurred while updating cars for owner with ID: {}. Exception: {}", registerCarRequestDto.getOwnerId(), e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating cars for owner with ID: {}. Exception: {}", registerCarRequestDto.getOwnerId(), e.getMessage(), e);
            throw ErrorException.internalError("Unexpected error while updating cars for owner ID: " + registerCarRequestDto.getOwnerId(), e);
        }
    }

    private RegisterCarResponseDto buildResponse(Owner owner, List<Car> updatedCars) {
        List<CarDto> carDtos = updatedCars.stream()
                .map(car -> new CarDto(car.getId(), car.getModel(), car.getVariant()))
                .collect(Collectors.toList());
        return new RegisterCarResponseDto(owner.getId(), owner.getName(), carDtos);
    }
    private List<Car> updateCars(Owner owner, List<CarDto> carDtoList) {
        return carDtoList.stream()
                .map(carDto -> {
                    Car car = owner.getCars().stream()
                            .filter(c -> c.getId().equals(carDto.getId()))
                            .findFirst()
                            .orElseThrow(() -> ErrorException.resourceNotExist("Car with ID: " + carDto.getId() + " not found"));
                    car.setModel(carDto.getModel());
                    car.setVariant(carDto.getVariant());
                    car.setStatus(CarStatus.IDLE);  // Reset status to IDLE
                    return car;
                })
                .collect(Collectors.toList());
    }


    private List<Car> mapCarDtosToEntities(List<CarDto> carDtoList, Owner owner) {
        return carDtoList.stream()
                .map(carDto -> Car.builder()
                        .model(carDto.getModel())
                        .variant(carDto.getVariant())
                        .status(CarStatus.IDLE)
                        .owner(owner)
                        .build())
                .collect(Collectors.toList());
    }
    private List<CarDto> mapCarsToCarDtos(List<Car> cars) {
        return cars.stream()
                .map(car -> new CarDto(car.getId(), car.getModel(), car.getVariant()))
                .collect(Collectors.toList());
    }
    private void updateOwnerDetails(Owner owner, UpdateOwnerDetailsRequestDto requestDto) {
        owner.setName(requestDto.getName());
        owner.setEmail(requestDto.getEmail());
        owner.setPhoneNumber(requestDto.getPhoneNumber());
    }

    private Car getCarByOwnerAndId(Owner owner, Integer carId) {
        return owner.getCars().stream()
                .filter(car -> car.getId().equals(carId))
                .findFirst()
                .orElseThrow(() -> ErrorException.resourceNotExist("Car with ID: " + carId + " not found for owner with ID: " + owner.getId()));
    }

}
