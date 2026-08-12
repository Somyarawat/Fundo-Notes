package com.fundoonotes.fundo_notes.service.implementation;

import com.fundoonotes.fundo_notes.dto.*;
import com.fundoonotes.fundo_notes.model.User;
import com.fundoonotes.fundo_notes.repository.UserRepository;
import com.fundoonotes.fundo_notes.service.UserService;
import com.fundoonotes.fundo_notes.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailUtil emailUtil;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    @Override
    public ApiResponse register(UserDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        String otp = generateOtp();

        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .mobileNumber(dto.getMobileNumber())
                .verified(false)
                .otp(otp)
                .otpExpiry(LocalDateTime.now().plusMinutes(10))
                .build();

        userRepository.save(user);

        emailUtil.sendOtp(user.getEmail(), otp);

        return new ApiResponse("Registration successful. OTP sent to email.");
    }

    @Override
    public ApiResponse verifyOtp(VerifyOtpDTO request) {

        User user = getUser(request.getEmail());

        if (user.isVerified()) {
            return new ApiResponse("User already verified");
        }

        if (!request.getOtp().equals(user.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (user.getOtpExpiry() == null ||
                user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        user.setVerified(true);
        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);

        return new ApiResponse("Email verified successfully");
    }

    @Override
    public ApiResponse resendOtp(VerifyOtpDTO request) {

        User user = getUser(request.getEmail());

        String otp = generateOtp();

        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));

        userRepository.save(user);

        emailUtil.sendOtp(user.getEmail(), otp);

        return new ApiResponse("New OTP sent successfully");
    }

    @Override
    public ApiResponse forgotPassword(ForgotPasswordDTO request) {

        User user = getUser(request.getEmail());

        String otp = generateOtp();

        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));

        userRepository.save(user);

        emailUtil.sendOtp(user.getEmail(), otp);

        return new ApiResponse("Password reset OTP sent to email");
    }

    @Override
    public ApiResponse resetPassword(ResetPasswordDTO request) {

        User user = getUser(request.getEmail());

        if (!request.getOtp().equals(user.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (user.getOtpExpiry() == null ||
                user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);

        return new ApiResponse("Password reset successfully");
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    private String generateOtp() {
        return String.valueOf(
                100000 + new Random().nextInt(900000)
        );
    }
}