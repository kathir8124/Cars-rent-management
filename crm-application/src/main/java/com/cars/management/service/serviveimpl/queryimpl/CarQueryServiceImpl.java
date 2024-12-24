package com.cars.management.service.serviveimpl.queryimpl;

import com.cars.management.dto.response.CarResponseDto;
import com.cars.management.dto.response.LeaseResponseDto;
import com.cars.management.entity.Car;
import com.cars.management.entity.Lease;
import com.cars.management.enums.CarStatus;
import com.cars.management.exception.ErrorException;
import com.cars.management.repository.queryrepository.CarQueryRepository;
import com.cars.management.repository.queryrepository.OwnerQueryRepository;
import com.cars.management.service.serviveports.queryports.CarQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarQueryServiceImpl implements CarQueryService {

     private final CarQueryRepository carQueryRepository;
     private final OwnerQueryRepository ownerQueryRepository;

    @Override
    public CarResponseDto getCarStatusAndDetails(Integer carId) {
        log.info("Received request to fetch car details for car ID: {}", carId);
        try {
            Car car = carQueryRepository.findById(carId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("CarNotFound", "Car not found with ID: ", carId));
            log.debug("Car details found for car ID: {}", carId);
            return mapCarToCarResponseDto(car);
        } catch (ErrorException e) {
            log.error("Error occurred while retrieving car details for car ID: {}. Error: {}", carId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving car details for car ID: {}. Error: {}", carId, e.getMessage(), e);
            throw ErrorException.internalError("InternalError", "Unexpected error while retrieving car details for car ID: " + carId);
        }
    }
    @Override
    public List<CarResponseDto> getCarsByStatus(CarStatus status) {
        log.info("Received request to fetch cars with status: {}", status);
        try {
            List<Car> cars = carQueryRepository.findByStatus(status.toString());
            if (cars.isEmpty()) {
                log.warn("No cars found with status: {}", status);
            } else {
                log.info("Successfully fetched {} cars with status: {}", cars.size(), status);
            }
            return cars.stream()
                    .map(this::mapCarToCarResponseDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            String errorMessage = "Error retrieving cars with status: " + status;
            log.error(errorMessage, e);
            throw ErrorException.internalError("InternalError", errorMessage, e);
        }
    }

    private CarResponseDto mapCarToCarResponseDto(Car car) {
        List<LeaseResponseDto> leaseResponseDtos = car.getLeases().stream()
                .map(this::mapLeaseToLeaseResponseDto)  // Map each Lease entity to LeaseResponseDto
                .collect(Collectors.toList());

        return CarResponseDto.builder()
                .id(car.getId())
                .model(car.getModel())
                .variant(car.getVariant())
                .status(car.getStatus())
                .leaseDtoList(leaseResponseDtos)
                .build();
    }
    private LeaseResponseDto mapLeaseToLeaseResponseDto(Lease lease) {
        return LeaseResponseDto.builder()
                .id(lease.getId())
                .startDate(lease.getStartDate())
                .endDate(lease.getEndDate())
                .status(lease.getStatus())
                .build();
    }
}
