package com.bekzodkeldiyarov.collectionstore.deserializer;

import com.bekzodkeldiyarov.collectionstore.model.ItemAttributeValue;
import com.bekzodkeldiyarov.collectionstore.service.AttributeService;
import com.bekzodkeldiyarov.collectionstore.service.ItemAttributeValueService;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ItemAttributeValueDeserializer extends JsonDeserializer {
    private final ItemAttributeValueService itemAttributeValueService;
    private final AttributeService attributeService;

    public ItemAttributeValueDeserializer(ItemAttributeValueService itemAttributeValueService, AttributeService attributeService) {
        this.itemAttributeValueService = itemAttributeValueService;
        this.attributeService = attributeService;
    }

    @Override
    public ItemAttributeValue deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        Long id = null;
        if (node.has("id")) {
            id = node.get("id").asLong();
        }
        final String value = node.get("value").asText();
        final long attribute = node.get("attribute").asLong();

        ItemAttributeValue itemAttributeValue;
        if (id != null) {
            itemAttributeValue = itemAttributeValueService.findById(id);
        } else {
            itemAttributeValue = new ItemAttributeValue();
            itemAttributeValue.setItem(null);
            itemAttributeValue.setAttribute(attributeService.findById(attribute));
        }
        itemAttributeValue.setValue(value);
        return itemAttributeValue;
    }
}
