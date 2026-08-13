package com.fundoonotes.fundo_notes.controller;

import com.fundoonotes.fundo_notes.dto.LoginDTO;
import com.fundoonotes.fundo_notes.dto.LoginResponseDTO;
import com.fundoonotes.fundo_notes.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginDTO loginDTO) {

        return ResponseEntity.ok(
                authService.login(loginDTO)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok(
                authService.logout()
        );
    }
}