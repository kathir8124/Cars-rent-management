package com.cars.management.service.serviveports.queryports;

import com.cars.management.dto.request.LeaseDto;
import com.cars.management.enums.LeaseStatus;

import java.util.List;

public interface LeaseQueryService {
    LeaseDto getLeaseDetailsById(Integer leaseId);

    List<LeaseDto> getLeaseDetailsByStatus(LeaseStatus status);

    List<LeaseDto> getAllLeaseDetails();
}
