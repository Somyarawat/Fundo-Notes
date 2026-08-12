package com.fundoonotes.fundo_notes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyOtpDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String otp;
}