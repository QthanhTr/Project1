package com.example.yourapp.controller;

import com.example.yourapp.model.Prescription;
import com.example.yourapp.service.PrescriptionService;
import com.example.yourapp.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final TokenService tokenService;

    public PrescriptionController(PrescriptionService prescriptionService, TokenService tokenService) {
        this.prescriptionService = prescriptionService;
        this.tokenService = tokenService;
    }

    /**
     * POST endpoint to save a new prescription. It validates the token
     * and the request body before saving.
     */
    @PostMapping
    public ResponseEntity<Object> savePrescription(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody Prescription prescription) {

        if (token == null || !token.startsWith("Bearer ") || !tokenService.validateToken(token.substring(7))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token.");
        }

        try {
            Prescription savedPrescription = prescriptionService.savePrescription(prescription);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPrescription);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving prescription: " + e.getMessage());
        }
    }
}
