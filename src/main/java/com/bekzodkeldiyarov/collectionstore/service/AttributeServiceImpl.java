package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.converters.AttributeCommandToAttribute;
import com.bekzodkeldiyarov.collectionstore.converters.AttributeToAttributeCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.repository.AttributeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
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
    public Attribute save(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    @Override
    public AttributeCommand saveAttributeCommand(AttributeCommand attributeCommand) {
        Attribute attributeToSave = attributeCommandToAttribute.convert(attributeCommand);
        Attribute savedAttribute = attributeRepository.save(attributeToSave);
        return attributeToAttributeCommand.convert(savedAttribute);
    }

    @Override
    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    @Override
    public List<Attribute> getAllAttributesOfCollection(Long collectionId) {
        List<Attribute> attributes = attributeRepository.findByCollectionId(collectionId);
        return attributes;
    }


    @Override
    public Set<Attribute> createAttributesFromHttpServletRequest(HttpServletRequest request) {
        Set<Attribute> attributes = new HashSet<>();
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String attributeNameKey = keys.nextElement();
            String attributeNameValue = request.getParameter(attributeNameKey);
            if (!attributeNameKey.contains("attribute_name")) {
                continue;
            }
            String attributeTypeKey = keys.nextElement();
            String attributeTypeValue = request.getParameter(attributeTypeKey);

            Attribute attribute = Attribute.builder().attributeName(attributeNameValue).type(attributeTypeValue).build();
            attributes.add(attribute);
        }
        return attributes;
    }

    @Override
    public List<Attribute> saveAttributes(List<Attribute> attributes) {
        List<Attribute> attributesToReturn = new ArrayList<>();
        for (Attribute attribute : attributes) {
            if (attribute.getId() != null) {
                Attribute attributeToSave = attributeRepository.findById(attribute.getId()).get();
                attributeToSave.setAttributeName(attribute.getAttributeName());
                attributeToSave.setType(attribute.getType());
                attributesToReturn.add(attributeRepository.save(attributeToSave));
            }
        }
        return attributesToReturn;
    }
}
