package com.cars.management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterCarRequestDto {
    @NotNull(message = "Owner id cannot be empty")
    private Integer ownerId;
    private List<CarDto> carDtoList;
}
