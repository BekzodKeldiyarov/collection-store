package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Item;

import java.util.List;
import java.util.Map;

public interface ItemService {
    Item save(Item collection);

    List<Item> getAllItems();

    Item getNewItemInstance(Long collectionId);

    Item findItemById(Long id);

    Item bindTagsToItem(Item item, String[] tags);
}
