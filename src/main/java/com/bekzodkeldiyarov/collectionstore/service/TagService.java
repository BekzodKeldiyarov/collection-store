package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    Tag save(Tag tag);

    List<Tag> save(Set<Tag> tags);

    List<Tag> getAllTags();

    Tag findByName(String name);
}
