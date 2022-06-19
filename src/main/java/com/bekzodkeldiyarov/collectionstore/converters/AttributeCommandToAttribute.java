package com.bekzodkeldiyarov.collectionstore.converters;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AttributeCommandToAttribute implements Converter<AttributeCommand, Attribute> {
    @Override
    public Attribute convert(AttributeCommand source) {
        Attribute attribute = new Attribute();
        attribute.setAttributeName(source.getAttributeName());
        attribute.setType(source.getType());
        attribute.setCollection(source.getCollection());

        return attribute;
    }
}
