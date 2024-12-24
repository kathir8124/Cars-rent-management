package com.cars.management.controller;

import com.cars.management.dto.request.CreateOwnerRequestDto;
import com.cars.management.dto.request.LeaseDto;
import com.cars.management.dto.request.RegisterCarRequestDto;
import com.cars.management.dto.request.UpdateOwnerDetailsRequestDto;
import com.cars.management.dto.response.CreateOwnerResponseDto;
import com.cars.management.dto.response.OwnerResponseDto;
import com.cars.management.dto.response.RegisterCarResponseDto;
import com.cars.management.dto.response.UpdateOwnerDetailsResponseDto;
import com.cars.management.service.serviveports.commandports.OwnerCommandService;
import com.cars.management.service.serviveports.queryports.OwnerQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/owner")
public class OwnerController {

    @Autowired
    private OwnerCommandService ownerCommandService;
    @Autowired
    private OwnerQueryService ownerQueryService;

    @Operation(summary = "Register car owner", description = "Register car owner")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception")})
//    {"name": "John Doe", "email": "john.doe@example.com", "phoneNumber": "+1234567890", "cars": [],"leases":[]}
    @PostMapping("/registerCarOwner")
    public ResponseEntity<CreateOwnerResponseDto> registerCarOwner(@RequestBody @Valid CreateOwnerRequestDto newOwnerDetails) {
        log.info("Received new car owner registration request: {}", newOwnerDetails);
        CreateOwnerResponseDto responseDto = ownerCommandService.createOwner(newOwnerDetails);
        log.info("Successfully registered new car owner with ID: {}", responseDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "Register cars for an owner", description = "Registers a list of cars for the given owner by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cars successfully registered"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Owner not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
//    [{"model": "Toyota Camry", "variant": "Hybrid"},{"model": "Honda Accord", "variant": "Sedan"}]
    @PostMapping("/registerCars")
    public ResponseEntity<RegisterCarResponseDto> registerCars(@RequestBody RegisterCarRequestDto registerCarRequestDto) {
        log.info("Received request to register cars for owner with ID: {}", registerCarRequestDto.getOwnerId());
        RegisterCarResponseDto responseDto = ownerCommandService.registerCar(registerCarRequestDto);
        log.info("Successfully registered cars for owner with ID: {}", responseDto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Delete an owner", description = "Deletes the owner based on the provided owner ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Owner not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/deleteOwner")
    public ResponseEntity<String> deleteOwnerById(@RequestParam Integer ownerId) {
        log.info("Received request to register cars for owner with ID: {}", ownerId);
        ownerCommandService.deleteOwnerById(ownerId);
        return new ResponseEntity<>("Owner deleted successfully with ID: " + ownerId, HttpStatus.OK);
    }

    @Operation(summary = "Update an owner by ID", description = "Updates the owner details such as name, email, and phone number by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid owner details"),
            @ApiResponse(responseCode = "404", description = "Owner not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/updateOwner")
    // {"id": 1, "name": "string", "email": "bbbb", "phoneNumber": "string"}
    // change the cardetails as well
    public ResponseEntity<UpdateOwnerDetailsResponseDto> updateOwner(
            @RequestBody UpdateOwnerDetailsRequestDto updateOwnerDetailsRequestDto) { // Owner details passed in the request body
        log.info("Update car owner details started: {}", updateOwnerDetailsRequestDto);
        UpdateOwnerDetailsResponseDto responseDto = ownerCommandService.updateOwnerById(updateOwnerDetailsRequestDto);
        log.info("Successfully updated car owner details with ID: {}", responseDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "Get owner details by ID", description = "Fetches the owner details based on the provided owner ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner found"),
            @ApiResponse(responseCode = "404", description = "Owner not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/getOwnerDetails")
    public ResponseEntity<OwnerResponseDto> getOwnerById(
            @RequestParam Integer ownerId) {
        log.info("Received request to fetch owner with ID: {}", ownerId);
        OwnerResponseDto owner = ownerQueryService.getOwnerById(ownerId);
        log.info("Successfully fetched owner with ID: {}", ownerId); // Log success
        return ResponseEntity.status(HttpStatus.OK).body(owner);
    }
    @Operation(summary = "Get all owners",
            description = "Fetches all owners from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owners fetched successfully"),
            @ApiResponse(responseCode = "204", description = "No owners found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/getAllOwners")
    public ResponseEntity<List<OwnerResponseDto>> getAllOwners() {
        log.info("Received request to fetch all owners.");
        List<OwnerResponseDto> owners = ownerQueryService.getAllOwners();
        if (owners.isEmpty()) {
            log.warn("No owners found in the system.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }
        log.info("Successfully fetched {} owners.", owners.size());
        return ResponseEntity.status(HttpStatus.OK).body(owners);
    }

    @Operation(summary = "Get lease history for an owner",
            description = "Fetches the lease history of an owner based on their owner ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lease history fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Owner not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/getLeaseHistoryByOwnerId")
    public ResponseEntity<List<LeaseDto>> getLeaseHistoryByOwnerId(
            @RequestParam Integer ownerId) {  // Owner ID passed as a query parameter
        log.info("Received request to fetch lease history for owner with ID: {}", ownerId);
        List<LeaseDto> leaseDtos = ownerQueryService.getLeaseHistoryByOwnerId(ownerId);
        log.info("Successfully fetched lease history for owner with ID: {}. Total leases: {}", ownerId, leaseDtos.size());
        return ResponseEntity.status(HttpStatus.OK).body(leaseDtos);
    }

    @Operation(summary = "Delete a car by owner ID and car ID",
            description = "Deletes a car from the system by the owner ID and car ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Owner or Car not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/deleteCarByOwnerIdAndCarId")
    public ResponseEntity<String> deleteCarByOwnerIdAndCarId(
            @RequestParam Integer ownerId, @RequestParam Integer carId) {
        log.info("Received request to delete car with ID: {} for owner with ID: {}", carId, ownerId);
        ownerCommandService.deleteCarByOwnerIdAndCarId(ownerId, carId);
        return new ResponseEntity<>("Car successfully deleted for owner with ID: " + ownerId + " and car ID: " + carId, HttpStatus.OK);
    }

    @Operation(summary = "Update car details", description = "Update the details of a car for a specific owner.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car details updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request, invalid input"),
            @ApiResponse(responseCode = "404", description = "Car or Owner not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/updateCarDetails")
    public ResponseEntity<RegisterCarResponseDto> updateCarDetails(@RequestBody RegisterCarRequestDto registerCarRequestDto) {
        log.info("Received request to update car details for owner with ID: {}", registerCarRequestDto.getOwnerId());
        RegisterCarResponseDto responseDto = ownerCommandService.updateCarDetails(registerCarRequestDto);
        log.info("Successfully updated car details for owner with ID: {} ", registerCarRequestDto.getOwnerId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
