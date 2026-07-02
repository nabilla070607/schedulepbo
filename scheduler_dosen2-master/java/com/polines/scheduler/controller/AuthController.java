package com.polines.scheduler.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        // Sementara hardcode untuk testing
        if ("admin@polines.ac.id".equals(request.getEmail()) && "admin123".equals(request.getPassword())) {
            return new AuthResponse("fake-jwt-token", "admin");
        } else if ("dosen@polines.ac.id".equals(request.getEmail()) && "dosen123".equals(request.getPassword())) {
            return new AuthResponse("fake-jwt-token", "dosen");
        } else if ("budi@mahasiswa.polines.ac.id".equals(request.getEmail()) && "mhs123".equals(request.getPassword())) {
            return new AuthResponse("fake-jwt-token", "mahasiswa");
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}

// --- Data Classes (bisa juga dipisah ke file sendiri) ---

@Data
class LoginRequest {
    private String email;
    private String password;
}

@Data
class AuthResponse {
    private String token;
    private String role;

    // Lombok @Data tidak membuat constructor dengan parameter.
    // Kita perlu membuat constructor manual agar bisa new AuthResponse("...", "...")
    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
}