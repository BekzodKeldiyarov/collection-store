package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.Item;

import java.util.List;
import java.util.Map;

public interface ItemService {
    Item save(Item collection);

    ItemCommand saveItemCommand(ItemCommand command);

    List<ItemCommand> getAllItemsOfCollection(Long id);

    ItemCommand getNewItemCommandInstance(Long collectionId);
}
