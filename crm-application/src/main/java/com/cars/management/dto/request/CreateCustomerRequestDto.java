package com.cars.management.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequestDto {

    @NotBlank(message = "Name cannot be blank.")
    @NotNull(message = "Name cannot be null")
    @Pattern(regexp = "^[a-zA-Z .-]*$", message = "Invalid name format")
    @Size(max = 30, message = "Name cannot exceed 30 characters.")
    private String name;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "^(?=.{1,50}$)[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Email is invalid")
    @Schema(description = "Email", example = "xxx@xxx.xxx")
    private String email;

    @NotNull(message = "Phone Number cannot be null")
    @NotBlank(message = "Phone Number cannot be empty")
    @Pattern(
            regexp = "^[+]?[0-9]{10,12}$",
            message = "Phone number must be valid and contain 10 to 15 digits. Optionally, it may start with '+'.")
    @Schema(description = "Phone Number", example = "+9199999999999")
    private String phoneNumber;
}
