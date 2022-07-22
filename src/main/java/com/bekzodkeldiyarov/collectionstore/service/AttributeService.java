package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import org.w3c.dom.Attr;

import java.util.List;


public interface AttributeService {
    Attribute save(Attribute attribute);

    List<Attribute> findAll();

    Attribute findById(Long id);
}
