package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.model.Collection;

import java.util.List;

public interface CollectionService {
    Collection save(Collection collection);

    CollectionCommand saveCollectionCommand(CollectionCommand collectionCommand);

    List<CollectionCommand> getAllCollectionCommands();

    CollectionCommand findCollectionCommandById(Long id);

    Collection findCollectionById(Long id);
}
