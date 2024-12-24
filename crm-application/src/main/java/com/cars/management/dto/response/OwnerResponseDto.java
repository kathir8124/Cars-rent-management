package com.cars.management.dto.response;

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
public class OwnerResponseDto {
    @JsonProperty("owner_id")
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<CarResponseDto> cars;
}
