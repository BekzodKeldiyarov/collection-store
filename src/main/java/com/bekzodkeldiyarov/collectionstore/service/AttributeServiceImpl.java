package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.repository.AttributeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;


    @Override
    public Attribute save(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    public AttributeServiceImpl(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    @Override
    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    @Override
    public Attribute findById(Long id) {
        return attributeRepository.findById(id).get();
    }
}
