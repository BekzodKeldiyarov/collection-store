package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.converters.AttributeCommandToAttribute;
import com.bekzodkeldiyarov.collectionstore.converters.AttributeToAttributeCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.repository.AttributeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeCommandToAttribute attributeCommandToAttribute;
    private final AttributeToAttributeCommand attributeToAttributeCommand;

    public AttributeServiceImpl(AttributeRepository attributeRepository, AttributeCommandToAttribute attributeCommandToAttribute, AttributeToAttributeCommand attributeToAttributeCommand) {
        this.attributeRepository = attributeRepository;
        this.attributeCommandToAttribute = attributeCommandToAttribute;
        this.attributeToAttributeCommand = attributeToAttributeCommand;
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
}
