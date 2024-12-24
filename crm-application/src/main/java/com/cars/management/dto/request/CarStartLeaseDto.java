package com.cars.management.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarStartLeaseDto {
    @JsonProperty("carId")
    private Integer id;
    private String model;
    private String variant;
}
