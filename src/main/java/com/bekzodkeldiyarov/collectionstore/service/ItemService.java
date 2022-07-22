package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Item;

import java.util.List;

public interface ItemService {
    Item save(Item collection);

    List<Item> getAllItems();

    List<Item> getAllItemsSortByLastAdded();

    Item getNewItemInstance(Long collectionId);

    Item findItemById(Long id);

    Item bindTagsToItem(Item item, String[] tags);

    void deleteById(Long id);
}
