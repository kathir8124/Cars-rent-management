package com.cars.management.service.serviveports.queryports;

import com.cars.management.dto.request.CustomerDto;
import com.cars.management.dto.request.LeaseDto;
import com.cars.management.dto.response.CarResponseDto;
import com.cars.management.enums.CarStatus;

import java.util.List;

public interface CustomerQueryService {
    CustomerDto getCustomerById(Integer customerId);

    List<LeaseDto> getLeaseHistoryByCustomerId(Integer customerId);

    List<CarResponseDto> viewCarsForLease(CarStatus status);

    List<CustomerDto> getAllCustomers();
}
