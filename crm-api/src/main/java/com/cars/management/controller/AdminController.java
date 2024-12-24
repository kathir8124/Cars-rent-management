package com.cars.management.controller;

import com.cars.management.dto.request.CreateCustomerRequestDto;
import com.cars.management.dto.request.CreateOwnerRequestDto;
import com.cars.management.dto.request.CustomerDto;
import com.cars.management.dto.request.LeaseDto;
import com.cars.management.dto.request.RegisterCarRequestDto;
import com.cars.management.dto.request.UpdateCustomerDetailsRequestDto;
import com.cars.management.dto.request.UpdateOwnerDetailsRequestDto;
import com.cars.management.dto.response.CarResponseDto;
import com.cars.management.dto.response.CreateCustomerResponseDto;
import com.cars.management.dto.response.CreateOwnerResponseDto;
import com.cars.management.dto.response.CustomerStartLeaseResponseDto;
import com.cars.management.dto.response.OwnerResponseDto;
import com.cars.management.dto.response.RegisterCarResponseDto;
import com.cars.management.dto.response.UpdateCustomerDetailsResponseDto;
import com.cars.management.dto.response.UpdateOwnerDetailsResponseDto;
import com.cars.management.enums.CarStatus;
import com.cars.management.enums.LeaseStatus;
import com.cars.management.service.serviveports.commandports.CustomerCommandService;
import com.cars.management.service.serviveports.commandports.OwnerCommandService;
import com.cars.management.service.serviveports.queryports.CarQueryService;
import com.cars.management.service.serviveports.queryports.CustomerQueryService;
import com.cars.management.service.serviveports.queryports.LeaseQueryService;
import com.cars.management.service.serviveports.queryports.OwnerQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private OwnerCommandService ownerCommandService;
    @Autowired
    private CarQueryService carQueryService;
    @Autowired
    private CustomerCommandService customerCommandService;
    @Autowired
    private CustomerQueryService customerQueryService;
    @Autowired
    private LeaseQueryService leaseQueryService;
    @Autowired
    private OwnerQueryService ownerQueryService;

    // Register car owner
    @Operation(summary = "Register car owner", description = "Register car owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registerCarOwner")
    public ResponseEntity<CreateOwnerResponseDto> registerCarOwner(@RequestBody @Valid CreateOwnerRequestDto newOwnerDetails) {
        log.info("Received new car owner registration request: {}", newOwnerDetails);
        CreateOwnerResponseDto responseDto = ownerCommandService.createOwner(newOwnerDetails);
        log.info("Successfully registered new car owner with ID: {}", responseDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Get car details by car ID
    @Operation(summary = "Get car details by car ID", description = "Fetches car details for the given car ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car details fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getCarDetailsByCarId")
    public ResponseEntity<CarResponseDto> getCarStatusAndDetails(@RequestParam Integer carId) {
        log.info("Received request to fetch car details for car ID: {}", carId);
        CarResponseDto car = carQueryService.getCarStatusAndDetails(carId);
        log.info("Successfully fetched car details for car ID: {}", carId);
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    // Get cars by status
    @Operation(summary = "Get cars by status", description = "Fetches all cars with the given status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cars with the status fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No cars found with the specified status"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getCarByStatus")
    public ResponseEntity<List<CarResponseDto>> getCarsByStatus(@RequestParam CarStatus status) {
        log.info("Received request to fetch cars with status: {}", status);
        List<CarResponseDto> cars = carQueryService.getCarsByStatus(status);
        log.info("Successfully fetched {} cars with status: {}", cars.size(), status);
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }

    @Operation(summary = "Register a new customer", description = "Registers a new customer into the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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

    @Operation(summary = "Get lease details by ID", description = "Fetches the details of a lease by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lease details fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Lease not found with the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getLeaseDetailsById")
    public ResponseEntity<LeaseDto> getLeaseDetailsById(@RequestParam Integer leaseId) {
        log.info("Received request to fetch lease details for lease ID: {}", leaseId);
        LeaseDto lease = leaseQueryService.getLeaseDetailsById(leaseId);
        log.info("Successfully fetched lease details for lease ID: {}", leaseId);
        return ResponseEntity.status(HttpStatus.OK).body(lease);
    }

    @Operation(summary = "Get lease details by status", description = "Fetches all leases by their status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leases fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No leases found with the given status"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getLeaseDetailsByStatus")
    public ResponseEntity<List<LeaseDto>> getLeaseDetailsByStatus(@RequestParam LeaseStatus status) {
        log.info("Received request to fetch leases with status: {}", status);
        List<LeaseDto> leases = leaseQueryService.getLeaseDetailsByStatus(status);
        log.info("Successfully fetched {} leases with status: {}", leases.size(), status);
        return ResponseEntity.status(HttpStatus.OK).body(leases);
    }

    @Operation(summary = "Get all lease details", description = "Fetches all lease details from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lease details fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No lease details found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllLeaseDetails")
    public ResponseEntity<List<LeaseDto>> getAllLeaseDetails() {
        log.info("Received request to fetch all lease details.");
        List<LeaseDto> leases = leaseQueryService.getAllLeaseDetails();
        log.info("Successfully fetched {} lease details.", leases.size());
        return ResponseEntity.status(HttpStatus.OK).body(leases);
    }
    @Operation(summary = "Register cars for an owner", description = "Registers a list of cars for the given owner by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cars successfully registered"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Owner not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
//    [{"model": "Toyota Camry", "variant": "Hybrid"},{"model": "Honda Accord", "variant": "Sedan"}]
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateCarDetails")
    public ResponseEntity<RegisterCarResponseDto> updateCarDetails(@RequestBody RegisterCarRequestDto registerCarRequestDto) {
        log.info("Received request to update car details for owner with ID: {}", registerCarRequestDto.getOwnerId());
        RegisterCarResponseDto responseDto = ownerCommandService.updateCarDetails(registerCarRequestDto);
        log.info("Successfully updated car details for owner with ID: {} ", registerCarRequestDto.getOwnerId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
