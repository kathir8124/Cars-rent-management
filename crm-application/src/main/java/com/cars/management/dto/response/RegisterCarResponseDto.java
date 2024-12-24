package com.cars.management.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.cars.management.dto.request.CarDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({ "ownerId", "name", "car" })
public class RegisterCarResponseDto {
    @JsonProperty("ownerId")
    private Integer id;
    private String name;
    private List<CarDto> car;
}
