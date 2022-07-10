package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface AttributeService {
    Attribute save(Attribute attribute);

    AttributeCommand saveAttributeCommand(AttributeCommand attributeCommand);

    List<Attribute> findAll();

    List<Attribute> getAllAttributesOfCollection(Long collectionId);

//    void bindAttributesToCollection(Set<AttributeCommand> attributeCommands, CollectionCommand collectionCommand);

    Set<Attribute> createAttributesFromHttpServletRequest(HttpServletRequest request);

    List<Attribute> saveAttributes(List<Attribute> attributes);
}
