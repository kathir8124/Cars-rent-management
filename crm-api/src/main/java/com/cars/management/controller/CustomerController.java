package com.cars.management.controller;

import com.cars.management.dto.request.CreateCustomerRequestDto;
import com.cars.management.dto.request.CustomerDto;
import com.cars.management.dto.request.LeaseDto;
import com.cars.management.dto.request.UpdateCustomerDetailsRequestDto;
import com.cars.management.dto.response.CarResponseDto;
import com.cars.management.dto.response.CreateCustomerResponseDto;
import com.cars.management.dto.response.CustomerStartLeaseResponseDto;
import com.cars.management.dto.response.UpdateCustomerDetailsResponseDto;
import com.cars.management.enums.CarStatus;
import com.cars.management.service.serviveports.commandports.CustomerCommandService;
import com.cars.management.service.serviveports.queryports.CustomerQueryService;
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
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    private CustomerCommandService customerCommandService;
    @Autowired
    private CustomerQueryService customerQueryService;

    @Operation(summary = "Register a new customer", description = "Registers a new customer into the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/registerCustomer")  // Post request to register a new customer
    public ResponseEntity<CreateCustomerResponseDto> registerCustomer(
            @RequestBody @Valid CreateCustomerRequestDto createCustomerRequestDto) {
        log.info("Received new car customer registration request: {}", createCustomerRequestDto);
        CreateCustomerResponseDto responseDto = customerCommandService.registerCustomer(createCustomerRequestDto);
        log.info("Successfully registered new customer with ID: {}", responseDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "Delete a customer by ID", description = "Deletes a customer from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<String> deleteCustomerById(
            @RequestParam Integer customerId) {
        log.info("Received request to delete customer with ID: {}", customerId);
        customerCommandService.deleteCustomerById(customerId);
        return new ResponseEntity<>("Customer deleted successfully with ID: " + customerId, HttpStatus.OK);
    }

    @Operation(summary = "Update customer details by ID", description = "Updates the details of an existing customer based on the provided customer ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully updated"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/update/updateCustomer")  // PUT request to update a customer by ID
    public ResponseEntity<UpdateCustomerDetailsResponseDto> updateCustomerById(@RequestBody UpdateCustomerDetailsRequestDto updateCustomerDetailsRequestDto) {
        log.info("Update customer details started: {}", updateCustomerDetailsRequestDto);
        UpdateCustomerDetailsResponseDto responseDto = customerCommandService.updateCustomerById(updateCustomerDetailsRequestDto);
        log.info("Successfully updated customer details with ID: {}", responseDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "Start a lease for a customer", description = "Starts a new lease for a customer with a given car if the customer is eligible and the car is available.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lease started successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Customer or car not found or car is not available"),
            @ApiResponse(responseCode = "409", description = "Conflict - Customer already has 2 active leases"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/startLease")  // Post request to start a lease
    public ResponseEntity<CustomerStartLeaseResponseDto> startLease(
            @RequestParam Integer customerId,
            @RequestParam Integer carId) {
        log.info("Received request to start lease for customer ID: {} and car ID: {}", customerId, carId);
        CustomerStartLeaseResponseDto responseDto = customerCommandService.startLease(customerId, carId);
        log.info("Successfully started lease for customer ID: {} and car ID: {}", customerId, carId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(
            summary = "End a lease",
            description = "Ends a lease for a given lease ID, marking the lease as ended and updating the car's status to available."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lease ended successfully"),
            @ApiResponse(responseCode = "404", description = "Lease not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid lease ID or error processing the lease"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/endLease")
    public ResponseEntity<LeaseDto> endLease(
            @RequestParam Integer leaseId) {
        log.info("Received request to end lease for lease ID: {}", leaseId);
        LeaseDto responseDto = customerCommandService.endLease(leaseId);
        log.info("Successfully request to end lease for lease ID: {}", leaseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid customer ID"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/getCustomerById")
    public ResponseEntity<CustomerDto> getCustomerById(
            @RequestParam Integer customerId) {
        log.info("Received request to fetch customer with ID: {}", customerId);
        CustomerDto customer = customerQueryService.getCustomerById(customerId);
        log.info("Successfully fetched customer with ID: {}", customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @Operation(summary = "Get lease history for a customer",
            description = "Fetches the lease history of a customer based on their customer ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lease history fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/getLeaseHistoryByCustomerId")
    public ResponseEntity<List<LeaseDto>> getLeaseHistoryByCustomerId(
            @RequestParam Integer customerId) {
        log.info("Received request to fetch lease history for customer with ID: {}", customerId);
        List<LeaseDto> leaseDtos = customerQueryService.getLeaseHistoryByCustomerId(customerId);
        log.info("Successfully fetched lease history for customer with ID: {}. Total leases: {}", customerId, leaseDtos.size());
        return ResponseEntity.status(HttpStatus.OK).body(leaseDtos);
    }

    @Operation(summary = "Get cars available for lease",
            description = "Fetches a list of cars that are available for lease based on the lease status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cars fetched successfully"),
            @ApiResponse(responseCode = "204", description = "No cars found for the given lease status"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/viewCarsForLease")
    public ResponseEntity<List<CarResponseDto>> viewCarsForLease() {
        log.info("Received request to fetch cars for lease with status: {}", CarStatus.IDLE);
        List<CarResponseDto> cars = customerQueryService.viewCarsForLease(CarStatus.IDLE);
        if (cars.isEmpty()) {
            log.warn("No cars found for lease with status: {}", CarStatus.IDLE);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }
        log.info("Successfully fetched {} cars for lease with status: {}", cars.size(), CarStatus.IDLE);
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }

    @Operation(summary = "Get all customers",
            description = "Fetches all customers from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owners fetched successfully"),
            @ApiResponse(responseCode = "204", description = "No owners found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<CustomerDto>> getAllCustomer() {
        log.info("Received request to fetch all customers.");
        List<CustomerDto> customers = customerQueryService.getAllCustomers();
        if (customers.isEmpty()) {
            log.warn("No customers found in the system.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }
        log.info("Successfully fetched {} customers.", customers.size());
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }


}
