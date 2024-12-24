package com.cars.management.service.serviveports.commandports;

import com.cars.management.dto.request.CreateCustomerRequestDto;
import com.cars.management.dto.request.LeaseDto;
import com.cars.management.dto.request.UpdateCustomerDetailsRequestDto;
import com.cars.management.dto.response.CreateCustomerResponseDto;
import com.cars.management.dto.response.CustomerStartLeaseResponseDto;
import com.cars.management.dto.response.UpdateCustomerDetailsResponseDto;

public interface CustomerCommandService {
    CreateCustomerResponseDto registerCustomer(CreateCustomerRequestDto createCustomerRequestDto);

    void deleteCustomerById(Integer customerId);

    UpdateCustomerDetailsResponseDto updateCustomerById(UpdateCustomerDetailsRequestDto updateCustomerDetailsRequestDto);

    // 3. Start a lease
    CustomerStartLeaseResponseDto startLease(Integer customerId, Integer carId);

    // 4. End a lease
    LeaseDto endLease(Integer leaseId);
}
