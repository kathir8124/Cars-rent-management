package com.cars.management.service.serviveimpl.queryimpl;

import com.cars.management.dto.request.LeaseDto;
import com.cars.management.dto.request.CarDto;
import com.cars.management.dto.response.CarResponseDto;
import com.cars.management.dto.response.LeaseResponseDto;
import com.cars.management.dto.response.OwnerResponseDto;
import com.cars.management.entity.Car;
import com.cars.management.entity.Lease;
import com.cars.management.entity.Owner;
import com.cars.management.enums.CarStatus;
import com.cars.management.exception.ErrorException;
import com.cars.management.repository.queryrepository.CarQueryRepository;
import com.cars.management.repository.queryrepository.LeaseQueryRepository;
import com.cars.management.repository.queryrepository.OwnerQueryRepository;
import com.cars.management.service.serviveports.queryports.OwnerQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerQueryServiceImpl implements OwnerQueryService {

    private final CarQueryRepository carQueryRepository;
    private final OwnerQueryRepository ownerQueryRepository;

    @Override
    public OwnerResponseDto getOwnerById(Integer ownerId) {
        log.info("Received request to fetch owner details for owner with ID: {}", ownerId);
        try {
            Owner owner = ownerQueryRepository.findById(ownerId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("OwnerNotFound", "Owner not found with ID: ", ownerId));
            log.debug("Owner found: {} - {}", owner.getId(), owner.getName());
            return mapOwnerToOwnerResponseDto(owner);
        } catch (ErrorException e) {
            log.error("Error occurred while fetching owner with ID: {}. Error: {}", ownerId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching owner with ID: {}. Error: {}", ownerId, e.getMessage(), e);
            throw ErrorException.internalError("InternalError", "Unexpected error while fetching owner with ID: " + ownerId);
        }
    }

    @Override
    public List<OwnerResponseDto> getAllOwners() {
        log.info("Received request to fetch all owners from the database.");
        try {
            List<Owner> owners = ownerQueryRepository.findAll();
            if (owners.isEmpty()) {
                log.warn("No owners found in the database.");
            } else {
                log.info("Successfully retrieved {} owners from the database.", owners.size());
            }
            return owners.stream()
                    .map(this::mapOwnerToOwnerResponseDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching owners. Error: {}", e.getMessage(), e);
            throw ErrorException.internalError("InternalError", "Unexpected error occurred while fetching owners.");
        }
    }


    @Override
    public List<LeaseDto> getLeaseHistoryByOwnerId(Integer ownerId) {
        log.info("Received request to fetch lease history for owner with ID: {}", ownerId);
        try {
            Owner owner = ownerQueryRepository.findById(ownerId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("OwnerNotFound", "Owner not found with ID: ", ownerId));
            log.debug("Owner found: {} - {}", owner.getId(), owner.getName());
            List<LeaseDto> leaseDtos = mapCarsToLeaseDtos(owner.getCars()); // Use the helper method
            log.info("Successfully retrieved {} leases for owner with ID: {}", leaseDtos.size(), ownerId);
            return leaseDtos;
        } catch (ErrorException e) {
            log.error("Error occurred while retrieving lease history for owner with ID: {}. Error: {}", ownerId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving lease history for owner with ID: {}. Error: {}", ownerId, e.getMessage(), e);
            throw ErrorException.internalError("InternalError", "Unexpected error while retrieving lease history for owner ID: " + ownerId);
        }
    }

    private List<LeaseDto> mapCarsToLeaseDtos(List<Car> cars) {
        return cars.stream()
                .flatMap(car -> car.getLeases().stream())
                .map(this::mapToLeaseDto)
                .collect(Collectors.toList());
    }
    private LeaseDto mapToLeaseDto(Lease lease) {
        CarDto carDto = new CarDto();
        carDto.setId(lease.getCar().getId());
        carDto.setModel(lease.getCar().getModel());
        carDto.setVariant(lease.getCar().getVariant());

        LeaseDto leaseDto = new LeaseDto();
        leaseDto.setId(lease.getId());
        leaseDto.setStartDate(lease.getStartDate());
        leaseDto.setEndDate(lease.getEndDate());
        leaseDto.setStatus(lease.getStatus());
        leaseDto.setCarDto(carDto);

        return leaseDto;
    }

    private OwnerResponseDto mapOwnerToOwnerResponseDto(Owner owner) {
        // Map the list of Car entities to CarResponseDto
        List<CarResponseDto> carResponseDtos = owner.getCars().stream()
                .map(this::mapCarToCarResponseDto)  // Map each Car entity to CarResponseDto
                .collect(Collectors.toList());

        // Build and return the OwnerResponseDto
        return OwnerResponseDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .email(owner.getEmail())
                .phoneNumber(owner.getPhoneNumber())
                .cars(carResponseDtos)
                .build();
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
