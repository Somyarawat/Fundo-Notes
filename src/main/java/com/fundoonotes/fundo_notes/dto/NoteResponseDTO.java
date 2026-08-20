package com.fundoonotes.fundo_notes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NoteResponseDTO {

    private Long id;
    private String title;
    private String description;
    private boolean pinned;
    private boolean archived;
    private boolean trashed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}