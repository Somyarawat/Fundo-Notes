package com.fundoonotes.fundo_notes.service.implementation;

import com.fundoonotes.fundo_notes.model.Note;
import com.fundoonotes.fundo_notes.model.Tag;
import com.fundoonotes.fundo_notes.model.User;
import com.fundoonotes.fundo_notes.repository.NoteRepository;
import com.fundoonotes.fundo_notes.repository.TagRepository;
import com.fundoonotes.fundo_notes.repository.UserRepository;
import com.fundoonotes.fundo_notes.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public Tag createTag(
            String email,
            String tagName) {

        User user = getUser(email);

        tagRepository.findByNameAndUser(tagName, user)
                .ifPresent(tag -> {
                    throw new RuntimeException(
                            "Tag already exists"
                    );
                });

        Tag tag = Tag.builder()
                .name(tagName)
                .user(user)
                .build();

        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllTags(String email) {

        User user = getUser(email);

        return tagRepository.findByUser(user);
    }

    @Override
    public void deleteTag(
            String email,
            Long tagId) {

        User user = getUser(email);

        Tag tag = tagRepository
                .findByIdAndUser(tagId, user)
                .orElseThrow(() ->
                        new RuntimeException("Tag not found"));

        for (Note note : tag.getNotes()) {
            note.getTags().remove(tag);
        }

        tagRepository.delete(tag);
    }

    @Override
    public void addTagToNote(
            String email,
            Long noteId,
            Long tagId) {

        User user = getUser(email);

        Note note = noteRepository
                .findByIdAndUser(noteId, user)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        Tag tag = tagRepository
                .findByIdAndUser(tagId, user)
                .orElseThrow(() ->
                        new RuntimeException("Tag not found"));

        note.getTags().add(tag);

        noteRepository.save(note);
    }

    @Override
    public void removeTagFromNote(
            String email,
            Long noteId,
            Long tagId) {

        User user = getUser(email);

        Note note = noteRepository
                .findByIdAndUser(noteId, user)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        Tag tag = tagRepository
                .findByIdAndUser(tagId, user)
                .orElseThrow(() ->
                        new RuntimeException("Tag not found"));

        note.getTags().remove(tag);

        noteRepository.save(note);
    }

    @Override
    public List<Tag> getTagsForNote(
            String email,
            Long noteId) {

        User user = getUser(email);

        Note note = noteRepository
                .findByIdAndUser(noteId, user)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        return List.copyOf(note.getTags());
    }

    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}