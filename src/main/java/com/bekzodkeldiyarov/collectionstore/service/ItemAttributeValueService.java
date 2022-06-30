package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.ItemAttributeValue;

import java.util.List;

public interface ItemAttributeValueService {
    ItemAttributeValue save(ItemAttributeValue itemAttributeValue);

    List<ItemAttributeValue> save(List<ItemAttributeValue> itemAttributeValues);

    List<ItemAttributeValue> findByItemId(Long id);
    ItemAttributeValue findById(Long id);
}
