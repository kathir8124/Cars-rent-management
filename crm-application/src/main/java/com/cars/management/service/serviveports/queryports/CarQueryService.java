package com.cars.management.service.serviveports.queryports;

import com.cars.management.dto.response.CarResponseDto;
import com.cars.management.enums.CarStatus;

import java.util.List;

public interface CarQueryService {
    CarResponseDto getCarStatusAndDetails(Integer carId);

    List<CarResponseDto> getCarsByStatus(CarStatus status);

}
