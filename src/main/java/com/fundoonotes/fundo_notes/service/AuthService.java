package com.fundoonotes.fundo_notes.service;

import com.fundoonotes.fundo_notes.dto.LoginDTO;
import com.fundoonotes.fundo_notes.dto.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginDTO loginDTO);

    String logout();
}