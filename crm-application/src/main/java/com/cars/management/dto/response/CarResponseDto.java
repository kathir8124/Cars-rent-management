package com.cars.management.dto.response;

import com.cars.management.enums.CarStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponseDto {
    @JsonProperty("carId")
    private Integer id;
    private String model;
    private String variant;
    private CarStatus status;
    private List<LeaseResponseDto> leaseDtoList;
}
