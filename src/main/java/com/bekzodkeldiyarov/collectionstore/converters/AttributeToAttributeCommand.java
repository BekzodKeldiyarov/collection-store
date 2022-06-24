package com.bekzodkeldiyarov.collectionstore.converters;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AttributeToAttributeCommand implements Converter<Attribute, AttributeCommand> {
    @Override
    public AttributeCommand convert(Attribute source) {

        AttributeCommand attributeCommand = AttributeCommand
                .builder()
                .id(source.getId())
                .attributeName(source.getAttributeName())
                .type(source.getType())
                .collection(source.getCollection()).build();
        return attributeCommand;
    }
}
