package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.models.CreateParkingBoyRequest;
import com.oocl.web.sampleWebApp.models.ParkingBoyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkingboys")
public class ParkingBoyResource {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @GetMapping
    public ResponseEntity<ParkingBoyResponse[]> getAll() {
        final ParkingBoyResponse[] parkingBoys = parkingBoyRepository.findAll().stream()
            .map((ParkingBoy employeeId) -> ParkingBoyResponse.create(employeeId.getEmployeeId()))
            .toArray(ParkingBoyResponse[]::new);

        return ResponseEntity.ok(parkingBoys);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CreateParkingBoyRequest request) {
        if (!request.isValid()) {
            return ResponseEntity.badRequest().build();
        }
        parkingBoyRepository.saveAndFlush(new ParkingBoy(request.getEmployeeId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
