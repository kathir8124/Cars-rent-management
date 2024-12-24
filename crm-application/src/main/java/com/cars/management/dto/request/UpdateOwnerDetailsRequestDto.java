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
public class UpdateOwnerDetailsRequestDto {
    @JsonProperty("ownerId")
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
}
