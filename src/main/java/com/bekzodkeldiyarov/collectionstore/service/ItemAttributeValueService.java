package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.ItemAttributeValue;

import java.util.List;

public interface ItemAttributeValueService {
    ItemAttributeValue save(ItemAttributeValue itemAttributeValue);

    List<ItemAttributeValue> save(List<ItemAttributeValue> itemAttributeValues);
}
