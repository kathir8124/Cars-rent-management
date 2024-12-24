package com.cars.management.service.serviveimpl.queryimpl;

import com.cars.management.dto.request.LeaseDto;
import com.cars.management.service.serviveports.queryports.LeaseQueryService;
import com.cars.management.dto.request.CarDto;
import com.cars.management.entity.Lease;
import com.cars.management.enums.LeaseStatus;
import com.cars.management.exception.ErrorException;
import com.cars.management.repository.queryrepository.LeaseQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeaseQueryServiceImpl implements LeaseQueryService {

    private final LeaseQueryRepository leaseQueryRepository;

    @Override
    public LeaseDto getLeaseDetailsById(Integer leaseId) {
        log.info("Received request to fetch lease details for lease ID: {}", leaseId);
        try {
            Lease lease = leaseQueryRepository.findById(leaseId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("LeaseNotFound", "Lease details not found with ID: ", leaseId));
            log.debug("Lease details found for lease ID: {}", leaseId);
            return mapToLeaseDto(lease);
        } catch (ErrorException e) {
            log.error("Error occurred while fetching lease details for lease ID: {}. Error: {}", leaseId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching lease details for lease ID: {}. Error: {}", leaseId, e.getMessage(), e);
            throw ErrorException.internalError("InternalError", "Unexpected error while fetching lease details for lease ID: " + leaseId);
        }
    }

    @Override
    public List<LeaseDto> getLeaseDetailsByStatus(LeaseStatus status) {
        log.info("Received request to fetch leases with status: {}", status);
        try {
            List<Lease> leases = leaseQueryRepository.findByLeaseByStatus(status.toString());
            if (leases.isEmpty()) {
                log.warn("No leases found with status: {}", status);
                return Collections.emptyList();
            }
            log.info("Successfully retrieved {} leases with status: {}", leases.size(), status);
            return leases.stream()
                    .map(this::mapToLeaseDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            String errorMessage = "Error retrieving leases with status: " + status;
            log.error(errorMessage, e);
            throw ErrorException.internalError("InternalError", errorMessage);
        }
    }

    @Override
    public List<LeaseDto> getAllLeaseDetails() {
        log.info("Received request to fetch all lease details.");
        try {
            List<Lease> leases = leaseQueryRepository.findAll();
            if (leases.isEmpty()) {
                log.warn("No lease details found in the database.");
                return Collections.emptyList();
            }
            log.info("Successfully fetched {} lease details.", leases.size());
            return leases.stream()
                    .map(this::mapToLeaseDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            String errorMessage = "Error occurred while fetching lease details.";
            log.error(errorMessage, e);
            throw ErrorException.internalError("InternalError", errorMessage, e);
        }
    }

    private LeaseDto mapToLeaseDto(Lease lease) {
        CarDto carDto = CarDto.builder()
                .id(lease.getCar().getId())
                .model(lease.getCar().getModel())
                .variant(lease.getCar().getVariant())
                .build();

        return LeaseDto.builder()
                .id(lease.getId())
                .startDate(lease.getStartDate())
                .endDate(lease.getEndDate())
                .status(lease.getStatus())
                .carDto(carDto)
                .build();
    }

}
