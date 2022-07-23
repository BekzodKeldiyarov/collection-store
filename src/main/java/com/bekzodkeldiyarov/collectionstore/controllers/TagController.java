package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.model.Tag;
import com.bekzodkeldiyarov.collectionstore.service.TagService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@Slf4j
@Api(description = "Tags data")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @PostMapping("/tags/add")
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.save(tag));
    }

    @PutMapping("/tags/{id}")
    public ResponseEntity<Tag> editTag(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.save(tag));
    }

    @DeleteMapping("/tags/{id}")
    public void deleteTagById(@PathVariable Long id) {
        tagService.deleteById(id);
    }
}
