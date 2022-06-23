package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;

import java.util.List;
import java.util.Set;

public interface AttributeService {
    AttributeCommand save(AttributeCommand attributeCommand);

    List<Attribute> findAll();

    List<AttributeCommand> getAllAttributesOfCollection(Long collectionId);

    void bindAttributesToCollection(Set<AttributeCommand> attributeCommands, CollectionCommand collectionCommand);
}
