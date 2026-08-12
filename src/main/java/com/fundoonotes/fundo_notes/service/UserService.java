package com.fundoonotes.fundo_notes.service;

import com.fundoonotes.fundo_notes.dto.*;

public interface UserService {

    ApiResponse register(UserDTO userDTO);

    ApiResponse verifyOtp(VerifyOtpDTO request);

    ApiResponse resendOtp(VerifyOtpDTO request);

    ApiResponse forgotPassword(ForgotPasswordDTO request);

    ApiResponse resetPassword(ResetPasswordDTO request);
}