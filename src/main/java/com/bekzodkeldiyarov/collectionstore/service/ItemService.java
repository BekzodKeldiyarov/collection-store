package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Item;

import java.util.List;

public interface ItemService {
    Item save(Item collection);

    List<Item> getAllItems();

    List<Item> getAllItemsSortByLastAdded();

    List<Item> getItemsOfCollectionId(Long id);

    Item getNewItemInstance(Long collectionId);

    Item findItemById(Long id);

    void deleteById(Long id);
}
