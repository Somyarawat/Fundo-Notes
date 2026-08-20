package com.fundoonotes.fundo_notes.service;

import com.fundoonotes.fundo_notes.model.Tag;

import java.util.List;

public interface TagService {

    Tag createTag(String email, String tagName);

    List<Tag> getAllTags(String email);

    void deleteTag(String email, Long tagId);

    void addTagToNote(
            String email,
            Long noteId,
            Long tagId
    );

    void removeTagFromNote(
            String email,
            Long noteId,
            Long tagId
    );

    List<Tag> getTagsForNote(
            String email,
            Long noteId
    );
}