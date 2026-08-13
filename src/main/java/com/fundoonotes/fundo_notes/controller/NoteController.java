package com.fundoonotes.fundo_notes.controller;

import com.fundoonotes.fundo_notes.dto.NoteDTO;
import com.fundoonotes.fundo_notes.dto.NoteResponseDTO;
import com.fundoonotes.fundo_notes.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(
            Authentication authentication,
            @Valid @RequestBody NoteDTO noteDTO) {

        String email = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(noteService.createNote(email, noteDTO));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getAllNotes(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.getAllNotes(email)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> getNoteById(
            Authentication authentication,
            @PathVariable Long id) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.getNoteById(email, id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> updateNote(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody NoteDTO noteDTO) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.updateNote(email, id, noteDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(
            Authentication authentication,
            @PathVariable Long id) {

        String email = authentication.getName();

        noteService.deleteNote(email, id);

        return ResponseEntity.ok("Note deleted successfully");
    }
}