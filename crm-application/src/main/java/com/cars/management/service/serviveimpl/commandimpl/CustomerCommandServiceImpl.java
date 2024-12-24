package com.cars.management.service.serviveimpl.commandimpl;

import com.cars.management.dto.request.CarDto;
import com.cars.management.dto.request.CreateCustomerRequestDto;
import com.cars.management.dto.request.LeaseDto;
import com.cars.management.dto.request.UpdateCustomerDetailsRequestDto;
import com.cars.management.dto.response.CreateCustomerResponseDto;
import com.cars.management.dto.response.CustomerStartLeaseResponseDto;
import com.cars.management.dto.response.UpdateCustomerDetailsResponseDto;
import com.cars.management.entity.Car;
import com.cars.management.entity.Customer;
import com.cars.management.entity.Lease;
import com.cars.management.enums.CarStatus;
import com.cars.management.enums.LeaseStatus;
import com.cars.management.exception.ErrorException;
import com.cars.management.repository.commandrepository.CarCommandRepository;
import com.cars.management.repository.commandrepository.CustomerCommandRepository;
import com.cars.management.repository.commandrepository.LeaseCommandRepository;
import com.cars.management.repository.queryrepository.CarQueryRepository;
import com.cars.management.repository.queryrepository.CustomerQueryRepository;
import com.cars.management.repository.queryrepository.LeaseQueryRepository;
import com.cars.management.service.serviveports.commandports.CustomerCommandService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerCommandServiceImpl implements CustomerCommandService {
     
     private final CustomerQueryRepository customerQueryRepository;
     private final CustomerCommandRepository customerCommandRepository;
     private final CarQueryRepository carQueryRepository;
     private final CarCommandRepository carCommandRepository;
     private final LeaseQueryRepository leaseQueryRepository;
     private final LeaseCommandRepository leaseCommandRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public CreateCustomerResponseDto registerCustomer(CreateCustomerRequestDto createCustomerRequestDto) {
        log.info("Starting customer registration process. Request: {}", createCustomerRequestDto); // Log start of process
        try {
            log.debug("Saving customer with details: {}", createCustomerRequestDto); // Log customer details being saved
            Customer savedCustomer = customerCommandRepository.save(modelMapper.map(createCustomerRequestDto, Customer.class));
            log.info("Customer registered successfully with ID: {}", savedCustomer.getId()); // Log success after saving
            return modelMapper.map(savedCustomer, CreateCustomerResponseDto.class);
        } catch (Exception e) {
            log.error("Error occurred while registering customer. Request Data: {}. Error: {}",
                    createCustomerRequestDto, e.getMessage(), e);
            throw ErrorException.internalError("Error while saving customer details: " + createCustomerRequestDto);
        }
    }


    @Override
    public void deleteCustomerById(Integer customerId) {
        log.info("Received request to delete customer with ID: {}", customerId);  // Log entry for the start of the request
        try {
            Customer customer = customerQueryRepository.findById(customerId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("Customer not found with ID: " + customerId));
            log.debug("Customer found: {} - {}", customer.getId(), customer.getName());
            log.info("Deleting customer with ID: {}", customerId);
            customerCommandRepository.deleteById(customerId);
            log.info("Successfully deleted customer with ID: {}", customerId);
        } catch (ErrorException e) {
            log.error("Error deleting customer with ID: {}. Exception: {}", customerId, e.getMessage(), e);
            throw e;
        } catch (RuntimeException e) {
            log.error("Error deleting customer with ID: {}. Exception: {}", customerId, e.getMessage(), e);
            throw ErrorException.internalError("Error while deleting customer with ID: " + customerId, e);
        }
    }

    @Override
    public UpdateCustomerDetailsResponseDto updateCustomerById(UpdateCustomerDetailsRequestDto updateCustomerDetailsRequestDto) {
        log.info("Received request to update customer with ID: {}", updateCustomerDetailsRequestDto.getId());
        try {
            Customer customer = customerQueryRepository.findById(updateCustomerDetailsRequestDto.getId())
                    .orElseThrow(() -> ErrorException.resourceNotExist("Customer not found with ID: " + updateCustomerDetailsRequestDto.getId()));
            log.debug("Customer found: {} - {}", customer.getId(), customer.getName());
            updateCustomerDetails(customer, updateCustomerDetailsRequestDto);
            Customer savedCustomer = customerCommandRepository.save(customer);
            log.info("Successfully updated customer with ID: {}", savedCustomer.getId());
            return modelMapper.map(savedCustomer, UpdateCustomerDetailsResponseDto.class);
        } catch (ErrorException e) {
            log.error("Error updating customer with ID: {}. Exception: {}", updateCustomerDetailsRequestDto.getId(), e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating customer with ID: {}. Exception: {}", updateCustomerDetailsRequestDto.getId(), e.getMessage(), e);
            throw ErrorException.internalError("Unexpected error while updating customer with ID: " + updateCustomerDetailsRequestDto.getId(), e);
        }
    }

    @Override
    public CustomerStartLeaseResponseDto startLease(Integer customerId, Integer carId) {
        log.info("Received request to start lease for customer ID: {} and car ID: {}", customerId, carId);

        try {
            Customer customer = customerCommandRepository.findById(customerId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("Customer not found with ID: " + customerId));
            Car car = carQueryRepository.findById(carId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("Car not found with ID: " + carId));
            validateCarAvailability(car);
            validateCustomerLeaseLimit(customerId);
            Lease lease = createLease(customer, car);
            car.setStatus(CarStatus.ON_LEASE);
            carCommandRepository.save(car);
            leaseCommandRepository.save(lease);
            log.info("Successfully started lease for customer ID: {} and car ID: {}", customerId, carId);
            return mapToCustomerStartLeaseResponse(customer);
        } catch (ErrorException e) {
            log.error("Error occurred while starting lease for customer ID: {} and car ID: {}. Exception: {}", customerId, carId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while starting lease for customer ID: {} and car ID: {}. Exception: {}", customerId, carId, e.getMessage(), e);
            throw ErrorException.internalError("Error while starting lease for customer ID: " + customerId + " and car ID: " + carId, e);
        }
    }


    @Override
    public LeaseDto endLease(Integer leaseId) {
        log.info("Received request to end lease with ID: {}", leaseId);
        try {
            Lease lease = leaseQueryRepository.findById(leaseId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("Lease not found with ID: " + leaseId));
            endLeaseAndUpdateCarStatus(lease);
            log.info("Lease with ID: {} successfully ended. Car status set to IDLE.", leaseId);
            return mapToEndLeaseDto(lease);
        } catch (ErrorException e) {
            log.error("Error occurred while ending lease with ID: {}. Error: {}", leaseId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while ending lease with ID: {}. Error: {}", leaseId, e.getMessage(), e);
            throw ErrorException.internalError("Error while ending lease with ID: " + leaseId, e);
        }
    }

    private void endLeaseAndUpdateCarStatus(Lease lease) {
        lease.setStatus(LeaseStatus.ENDED);
        lease.setEndDate(LocalDateTime.now());

        Car car = lease.getCar();
        car.setStatus(CarStatus.IDLE);

        // Save the updated car and lease
        carCommandRepository.save(car);
        leaseCommandRepository.save(lease);
    }
    private LeaseDto mapToEndLeaseDto(Lease lease) {
        LeaseDto leaseDto = new LeaseDto();
        leaseDto.setId(lease.getId());
        leaseDto.setStartDate(lease.getStartDate());
        leaseDto.setEndDate(lease.getEndDate());
        leaseDto.setStatus(lease.getStatus());

        CarDto carDto = new CarDto();
        carDto.setId(lease.getCar().getId());
        carDto.setModel(lease.getCar().getModel());
        carDto.setVariant(lease.getCar().getVariant());
        leaseDto.setCarDto(carDto);

        return leaseDto;
    }


    private void updateCustomerDetails(Customer customer, UpdateCustomerDetailsRequestDto requestDto) {
        customer.setName(requestDto.getName());
        customer.setEmail(requestDto.getEmail());
        customer.setPhoneNumber(requestDto.getPhoneNumber());
    }

    private void validateCarAvailability(Car car) {
        if (car.getStatus() != CarStatus.IDLE) {
            throw ErrorException.internalError("Car is not available for lease");
        }
    }

    private void validateCustomerLeaseLimit(Integer customerId) {
        List<Lease> activeLeases = leaseQueryRepository.findByCustomerIdAndStatus(customerId, LeaseStatus.ACTIVE.toString());
        if (activeLeases.size() >= 2) {
            throw ErrorException.internalError("Customer already has 2 active leases");
        }
    }
    private Lease createLease(Customer customer, Car car) {
        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setStatus(LeaseStatus.ACTIVE);
        lease.setStartDate(LocalDateTime.now());
        return lease;
    }
    private CustomerStartLeaseResponseDto mapToCustomerStartLeaseResponse(Customer customer) {
        List<LeaseDto> leaseDtos = customer.getLeases().stream().map(this::mapToLeaseDto).collect(Collectors.toList());
        CustomerStartLeaseResponseDto responseDto = new CustomerStartLeaseResponseDto();
        responseDto.setId(customer.getId());
        responseDto.setName(customer.getName());
        responseDto.setLeaseDtoList(leaseDtos);
        return responseDto;
    }

    private LeaseDto mapToLeaseDto(Lease lease) {
        LeaseDto leaseDto = new LeaseDto();
        leaseDto.setId(lease.getId());
        leaseDto.setStartDate(lease.getStartDate());
        leaseDto.setEndDate(lease.getEndDate());
        leaseDto.setStatus(lease.getStatus());

        CarDto carDto = new CarDto();
        carDto.setId(lease.getCar().getId());
        carDto.setModel(lease.getCar().getModel());
        carDto.setVariant(lease.getCar().getVariant());
        leaseDto.setCarDto(carDto);

        return leaseDto;
    }


}

