package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Tag;
import com.bekzodkeldiyarov.collectionstore.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> save(Set<Tag> tags) {
        return tagRepository.saveAll(tags);
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).get();
    }

    @Override
    public List<Tag> getAllTags() {

        return tagRepository.findAll();
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }
}
