package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.Item;

import java.util.List;

public interface ItemService {
    Item save(Item collection);

    List<ItemCommand> getAllItemsOfCollection(Long id);
}
