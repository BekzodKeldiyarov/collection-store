package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;

import java.util.List;

public interface AttributeService {
    AttributeCommand save(AttributeCommand attributeCommand);
    List<Attribute> findAll();
}
