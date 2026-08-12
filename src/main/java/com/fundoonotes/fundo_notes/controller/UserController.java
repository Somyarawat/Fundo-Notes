package com.fundoonotes.fundo_notes.controller;

import com.fundoonotes.fundo_notes.dto.*;
import com.fundoonotes.fundo_notes.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody UserDTO userDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.register(userDTO));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(
            @Valid @RequestBody VerifyOtpDTO request) {

        return ResponseEntity.ok(
                userService.verifyOtp(request)
        );
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse> resendOtp(
            @Valid @RequestBody VerifyOtpDTO request) {

        return ResponseEntity.ok(
                userService.resendOtp(request)
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(
            @Valid @RequestBody ForgotPasswordDTO request) {

        return ResponseEntity.ok(
                userService.forgotPassword(request)
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(
            @Valid @RequestBody ResetPasswordDTO request) {

        return ResponseEntity.ok(
                userService.resetPassword(request)
        );
    }
}