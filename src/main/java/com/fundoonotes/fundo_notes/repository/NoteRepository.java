package com.fundoonotes.fundo_notes.repository;

import com.fundoonotes.fundo_notes.model.Note;
import com.fundoonotes.fundo_notes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUser(User user);

    Optional<Note> findByIdAndUser(Long id, User user);

    List<Note> findByUserAndTitleContainingIgnoreCase(
            User user,
            String title
    );

    List<Note> findByUserAndDescriptionContainingIgnoreCase(
            User user,
            String description
    );
}