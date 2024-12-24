package com.cars.management.dto.request;

import com.cars.management.enums.LeaseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaseDto {
    @JsonProperty("lease_id")
    private Integer id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LeaseStatus status;
    private CarDto carDto;
}