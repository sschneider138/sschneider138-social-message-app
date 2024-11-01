package com.messaging.app.backend.Tags;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestParam String name) {
        Tag tag = tagService.createTag(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(tag);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name) {
        return tagService.getTagByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }
}
