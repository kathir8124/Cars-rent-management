package com.cars.management.controller;

import com.cars.management.dto.request.LeaseDto;
import com.cars.management.enums.LeaseStatus;
import com.cars.management.service.serviveports.queryports.LeaseQueryService;
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
@RequestMapping("api/lease")
public class LeaseController {
    @Autowired
    private LeaseQueryService leaseQueryService;

    @Operation(summary = "Get lease details by ID", description = "Fetches the details of a lease by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lease details fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Lease not found with the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
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
    @GetMapping("/getAllLeaseDetails")
    public ResponseEntity<List<LeaseDto>> getAllLeaseDetails() {
        log.info("Received request to fetch all lease details.");
        List<LeaseDto> leases = leaseQueryService.getAllLeaseDetails();
        log.info("Successfully fetched {} lease details.", leases.size());
        return ResponseEntity.status(HttpStatus.OK).body(leases);
    }


}
