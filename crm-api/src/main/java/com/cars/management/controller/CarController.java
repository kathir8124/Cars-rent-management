package com.cars.management.controller;

import com.cars.management.dto.response.CarResponseDto;
import com.cars.management.enums.CarStatus;
import com.cars.management.service.serviveports.queryports.CarQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/car")
public class CarController {
    @Autowired
    private CarQueryService carQueryService;

    @Operation(summary = "Get car details by car ID",
            description = "Fetches car details for the given car ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car details fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/getCarDetailsByCarId")
    public ResponseEntity<CarResponseDto> getCarStatusAndDetails(@RequestParam Integer carId) {
        log.info("Received request to fetch car details for car ID: {}", carId);
        CarResponseDto car = carQueryService.getCarStatusAndDetails(carId);
        log.info("Successfully fetched car details for car ID: {}", carId);
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @Operation(summary = "Get cars by status",
            description = "Fetches all cars with the given status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cars with the status fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No cars found with the specified status"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/getCarByStatus")
    public ResponseEntity<List<CarResponseDto>> getCarsByStatus(@RequestParam CarStatus status) {
        log.info("Received request to fetch cars with status: {}", status);
        List<CarResponseDto> cars = carQueryService.getCarsByStatus(status);
        log.info("Successfully fetched {} cars with status: {}", cars.size(), status);
        return ResponseEntity.status(HttpStatus.OK).body(cars);

    }

}
