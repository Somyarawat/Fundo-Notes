package com.fundoonotes.fundo_notes.service;

import com.fundoonotes.fundo_notes.dto.NoteDTO;
import com.fundoonotes.fundo_notes.dto.NoteResponseDTO;

import java.util.List;

public interface NoteService {

    NoteResponseDTO createNote(String email, NoteDTO noteDTO);

    List<NoteResponseDTO> getAllNotes(String email);

    NoteResponseDTO getNoteById(String email, Long noteId);

    NoteResponseDTO updateNote(
            String email,
            Long noteId,
            NoteDTO noteDTO
    );

    void deleteNote(String email, Long noteId);

    void togglePin(String email, Long noteId);

    void toggleArchive(String email, Long noteId);

    void moveToTrash(String email, Long noteId);

    void restoreFromTrash(String email, Long noteId);
}