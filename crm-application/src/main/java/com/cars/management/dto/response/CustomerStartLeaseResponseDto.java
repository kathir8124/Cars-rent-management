package com.cars.management.dto.response;

import com.cars.management.dto.request.LeaseDto;
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
public class CustomerStartLeaseResponseDto {
    @JsonProperty("customerId")
    private Integer id;
    @JsonProperty("customerName")
    private String name;
    private List<LeaseDto> leaseDtoList;
}
