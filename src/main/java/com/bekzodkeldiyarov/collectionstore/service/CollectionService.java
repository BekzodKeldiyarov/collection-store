package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.Collection;

import java.util.List;
import java.util.Set;

public interface CollectionService {
    Collection save(Collection collection);

    CollectionCommand saveCollectionCommand(CollectionCommand collectionCommand);

    CollectionCommand saveCollectionCommand(CollectionCommand collectionCommand, Set<Attribute> attributes);

    List<CollectionCommand> getAllCollectionCommands();

    List<Collection> getAllCollectionsOfUser(Long userId);

    CollectionCommand findCollectionCommandById(Long id);

    Collection findCollectionById(Long id);
}
