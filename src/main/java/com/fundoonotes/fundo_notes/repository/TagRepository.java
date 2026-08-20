package com.fundoonotes.fundo_notes.repository;

import com.fundoonotes.fundo_notes.model.Tag;
import com.fundoonotes.fundo_notes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByUser(User user);

    Optional<Tag> findByIdAndUser(Long id, User user);

    Optional<Tag> findByNameAndUser(String name, User user);
}