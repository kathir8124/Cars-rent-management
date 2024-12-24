package com.cars.management.service.serviveports.queryports;

import com.cars.management.dto.request.LeaseDto;
import com.cars.management.dto.response.OwnerResponseDto;

import java.util.List;

public interface OwnerQueryService {
    OwnerResponseDto getOwnerById(Integer ownerId);

    List<OwnerResponseDto> getAllOwners();

    List<LeaseDto> getLeaseHistoryByOwnerId(Integer ownerId);

}
