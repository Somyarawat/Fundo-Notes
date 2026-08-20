package com.fundoonotes.fundo_notes.service.implementation;

import com.fundoonotes.fundo_notes.dto.NoteDTO;
import com.fundoonotes.fundo_notes.dto.NoteResponseDTO;
import com.fundoonotes.fundo_notes.model.Note;
import com.fundoonotes.fundo_notes.model.User;
import com.fundoonotes.fundo_notes.repository.NoteRepository;
import com.fundoonotes.fundo_notes.repository.UserRepository;
import com.fundoonotes.fundo_notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public NoteResponseDTO createNote(
            String email,
            NoteDTO noteDTO) {

        User user = getUser(email);

        Note note = Note.builder()
                .title(noteDTO.getTitle())
                .description(noteDTO.getDescription())
                .user(user)
                .build();

        Note savedNote = noteRepository.save(note);

        return convertToResponse(savedNote);
    }

    @Override
    public List<NoteResponseDTO> getAllNotes(String email) {

        User user = getUser(email);

        return noteRepository.findByUser(user)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public NoteResponseDTO getNoteById(
            String email,
            Long noteId) {

        User user = getUser(email);

        Note note = noteRepository
                .findByIdAndUser(noteId, user)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        return convertToResponse(note);
    }

    @Override
    public NoteResponseDTO updateNote(
            String email,
            Long noteId,
            NoteDTO noteDTO) {

        User user = getUser(email);

        Note note = noteRepository
                .findByIdAndUser(noteId, user)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());

        Note updatedNote = noteRepository.save(note);

        return convertToResponse(updatedNote);
    }

    @Override
    public void deleteNote(
            String email,
            Long noteId) {

        User user = getUser(email);

        Note note = noteRepository
                .findByIdAndUser(noteId, user)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        noteRepository.delete(note);
    }

    @Override
    public void togglePin(String email, Long noteId) {

        User user = getUser(email);

        Note note = noteRepository
                .findByIdAndUser(noteId, user)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        note.setPinned(!note.isPinned());

        noteRepository.save(note);
    }

    @Override
    public void toggleArchive(String email, Long noteId) {

        User user = getUser(email);

        Note note = noteRepository
                .findByIdAndUser(noteId, user)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        note.setArchived(!note.isArchived());

        noteRepository.save(note);
    }

    @Override
    public void moveToTrash(String email, Long noteId) {

        User user = getUser(email);

        Note note = noteRepository
                .findByIdAndUser(noteId, user)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        note.setTrashed(true);

        noteRepository.save(note);
    }

    @Override
    public void restoreFromTrash(String email, Long noteId) {

        User user = getUser(email);

        Note note = noteRepository
                .findByIdAndUser(noteId, user)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        note.setTrashed(false);

        noteRepository.save(note);
    }

    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    private NoteResponseDTO convertToResponse(Note note) {

        return new NoteResponseDTO(
                note.getId(),
                note.getTitle(),
                note.getDescription(),
                note.isPinned(),
                note.isArchived(),
                note.isTrashed(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }
}