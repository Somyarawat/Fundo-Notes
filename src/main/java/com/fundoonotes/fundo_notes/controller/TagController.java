package com.fundoonotes.fundo_notes.controller;

import com.fundoonotes.fundo_notes.model.Tag;
import com.fundoonotes.fundo_notes.service.TagService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Tag> createTag(
            Authentication authentication,
            @RequestParam String name) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        tagService.createTag(
                                authentication.getName(),
                                name
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags(
            Authentication authentication) {

        return ResponseEntity.ok(
                tagService.getAllTags(
                        authentication.getName()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(
            Authentication authentication,
            @PathVariable Long id) {

        tagService.deleteTag(
                authentication.getName(),
                id
        );

        return ResponseEntity.ok(
                "Tag deleted successfully"
        );
    }

    @PostMapping("/note/{noteId}/tag/{tagId}")
    public ResponseEntity<String> addTagToNote(
            Authentication authentication,
            @PathVariable Long noteId,
            @PathVariable Long tagId) {

        tagService.addTagToNote(
                authentication.getName(),
                noteId,
                tagId
        );

        return ResponseEntity.ok(
                "Tag added to note successfully"
        );
    }

    @DeleteMapping("/note/{noteId}/tag/{tagId}")
    public ResponseEntity<String> removeTagFromNote(
            Authentication authentication,
            @PathVariable Long noteId,
            @PathVariable Long tagId) {

        tagService.removeTagFromNote(
                authentication.getName(),
                noteId,
                tagId
        );

        return ResponseEntity.ok(
                "Tag removed from note successfully"
        );
    }

    @GetMapping("/note/{noteId}")
    public ResponseEntity<List<Tag>> getTagsForNote(
            Authentication authentication,
            @PathVariable Long noteId) {

        return ResponseEntity.ok(
                tagService.getTagsForNote(
                        authentication.getName(),
                        noteId
                )
        );
    }
}