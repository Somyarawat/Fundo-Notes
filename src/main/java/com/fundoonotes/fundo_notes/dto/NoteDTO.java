package com.fundoonotes.fundo_notes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NoteDTO {

    @NotBlank
    private String title;

    private String description;
}