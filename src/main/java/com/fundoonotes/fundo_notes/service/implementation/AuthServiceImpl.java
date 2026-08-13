package com.fundoonotes.fundo_notes.service.implementation;

import com.fundoonotes.fundo_notes.dto.LoginDTO;
import com.fundoonotes.fundo_notes.dto.LoginResponseDTO;
import com.fundoonotes.fundo_notes.model.User;
import com.fundoonotes.fundo_notes.repository.UserRepository;
import com.fundoonotes.fundo_notes.security.JwtUtil;
import com.fundoonotes.fundo_notes.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!user.isVerified()) {
            throw new RuntimeException("Please verify your email first");
        }

        if (!passwordEncoder.matches(
                loginDTO.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponseDTO(
                "Login successful",
                token
        );
    }

    @Override
    public String logout() {
        return "Logout successful. Please discard the JWT token.";
    }
}