package com.cars.management.service.serviveports.commandports;

import com.cars.management.dto.request.CreateOwnerRequestDto;
import com.cars.management.dto.request.RegisterCarRequestDto;
import com.cars.management.dto.request.UpdateOwnerDetailsRequestDto;
import com.cars.management.dto.response.CreateOwnerResponseDto;
import com.cars.management.dto.response.RegisterCarResponseDto;
import com.cars.management.dto.response.UpdateOwnerDetailsResponseDto;

public interface OwnerCommandService {

    CreateOwnerResponseDto createOwner(CreateOwnerRequestDto createOwnerRequestDto);

    RegisterCarResponseDto registerCar(RegisterCarRequestDto registerCarRequestDto);

    void deleteOwnerById(Integer ownerId);

    UpdateOwnerDetailsResponseDto updateOwnerById(UpdateOwnerDetailsRequestDto updateOwnerDetailsRequestDto);

    void deleteCarByOwnerIdAndCarId(Integer ownerId, Integer carId);

    RegisterCarResponseDto updateCarDetails(RegisterCarRequestDto registerCarRequestDto);
}
