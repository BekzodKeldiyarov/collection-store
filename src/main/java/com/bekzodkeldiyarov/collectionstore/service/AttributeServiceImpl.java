package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.converters.AttributeCommandToAttribute;
import com.bekzodkeldiyarov.collectionstore.converters.AttributeToAttributeCommand;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionCommandToCollection;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.repository.AttributeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeCommandToAttribute attributeCommandToAttribute;
    private final AttributeToAttributeCommand attributeToAttributeCommand;
    private final CollectionCommandToCollection collectionCommandToCollection;

    public AttributeServiceImpl(AttributeRepository attributeRepository, AttributeCommandToAttribute attributeCommandToAttribute, AttributeToAttributeCommand attributeToAttributeCommand, CollectionCommandToCollection collectionCommandToCollection) {
        this.attributeRepository = attributeRepository;
        this.attributeCommandToAttribute = attributeCommandToAttribute;
        this.attributeToAttributeCommand = attributeToAttributeCommand;
        this.collectionCommandToCollection = collectionCommandToCollection;
    }

    @Override
    public AttributeCommand save(AttributeCommand attributeCommand) {
        Attribute attributeToSave = attributeCommandToAttribute.convert(attributeCommand);
        Attribute savedAttribute = attributeRepository.save(attributeToSave);
        return attributeToAttributeCommand.convert(savedAttribute);
    }

    @Override
    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    @Override
    public List<AttributeCommand> getAllAttributesOfCollection(Long collectionId) {
        List<Attribute> attributes = attributeRepository.findByCollectionId(collectionId);
        List<AttributeCommand> attributeCommands = new ArrayList<>();
        for (Attribute attribute : attributes) {
            attributeCommands.add(attributeToAttributeCommand.convert(attribute));
        }
        return attributeCommands;
    }

    @Override
    public void bindAttributesToCollection(Set<AttributeCommand> attributeCommands, CollectionCommand collectionCommand) {
        Collection collection = collectionCommandToCollection.convert(collectionCommand);
        for (AttributeCommand attributeCommand : attributeCommands) {
            Attribute attribute = attributeCommandToAttribute.convert(attributeCommand);
            if (attribute != null && collection != null) {
                attribute.setCollection(collection);
                collection.getAttributes().add(attribute);
                attributeRepository.save(attribute);
            }
        }
    }
}
