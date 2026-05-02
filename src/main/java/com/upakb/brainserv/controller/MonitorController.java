package com.upakb.brainserv.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import com.upakb.brainserv.dto.AppStatusDTO;
import com.upakb.brainserv.service.Pm2MonitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monitor")
@CrossOrigin(origins = "*") // Update this to your domain later for security
public class MonitorController {

    private final Pm2MonitorService monitorService;

    // Inject the key from properties
    @Value("${sentinel.api.key}")
    private String validApiKey;

    public MonitorController(Pm2MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping("/status")
    public ResponseEntity<?> getSystemStatus(@RequestHeader(value = "X-Sentinel-Key", required = false) String providedKey) {
        // Simple security check
        if (providedKey == null || !providedKey.equals(validApiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing API Key");
        }

        try {
            return ResponseEntity.ok(monitorService.getProcessedStatus());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching PM2 status: " + e.getMessage());
        }
    }
}
