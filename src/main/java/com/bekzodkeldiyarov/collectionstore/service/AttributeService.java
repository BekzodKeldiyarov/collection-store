package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.Collection;

import java.util.List;

public interface AttributeService {
    Attribute save(Attribute attribute);
    List<Attribute> findAll();
}