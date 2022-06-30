package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionCommandToCollection;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionToCollectionCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;
    private final CollectionToCollectionCommand collectionToCollectionCommand;
    private final CollectionCommandToCollection collectionCommandToCollection;

    private final UserService userService;
    @Autowired
    private AttributeService attributeService;

    public CollectionServiceImpl(CollectionRepository collectionRepository, CollectionToCollectionCommand collectionToCollectionCommand, CollectionCommandToCollection collectionCommandToCollection, UserService userService) {
        this.collectionRepository = collectionRepository;
        this.collectionToCollectionCommand = collectionToCollectionCommand;
        this.collectionCommandToCollection = collectionCommandToCollection;
        this.userService = userService;
    }

    @Override
    public Collection save(Collection collection) {
        return collectionRepository.save(collection);
    }

    @Override
    public CollectionCommand saveCollectionCommand(CollectionCommand collectionCommand) {
        Collection collectionToSave = collectionCommandToCollection.convert(collectionCommand);
        collectionToSave.setUser(userService.findByUsername("admin"));
        Collection savedCollection = collectionRepository.save(collectionToSave);
        CollectionCommand savedCollectionCommand = collectionToCollectionCommand.convert(savedCollection);
        return savedCollectionCommand;
    }

    @Override
    public CollectionCommand saveCollectionCommand(CollectionCommand collectionCommand, Set<Attribute> attributes) {
        Collection collectionToSave = collectionCommandToCollection.convert(collectionCommand);
        collectionToSave.setUser(userService.findByUsername("admin"));
        Collection savedCollection = new Collection();
        for (Attribute attribute : attributes) {
            attribute.setCollection(collectionToSave);
            collectionToSave.getAttributes().add(attribute);
            savedCollection = collectionRepository.save(collectionToSave);
            attributeService.save(attribute);
        }
        CollectionCommand savedCollectionCommand = collectionToCollectionCommand.convert(savedCollection);
        return savedCollectionCommand;
    }

    @Override
    public List<CollectionCommand> getAllCollectionCommands() {
        List<Collection> collections = collectionRepository.findAll();
        List<CollectionCommand> collectionCommands = new ArrayList<>();
        for (Collection collection : collections) {
            collectionCommands.add(collectionToCollectionCommand.convert(collection));
        }
        return collectionCommands;
    }

    @Override
    public List<Collection> getAllCollectionsOfUser(Long userId) {
        return collectionRepository.findAllByUserId(userId);
    }

    @Override
    public CollectionCommand findCollectionCommandById(Long id) {
        Collection foundCollection = new Collection();
        if (collectionRepository.findById(id).isPresent()) {
            foundCollection = collectionRepository.findById(id).get();
        }
        return collectionToCollectionCommand.convert(foundCollection);
    }

    @Override
    public Collection findCollectionById(Long id) {
        Collection collection = new Collection();
        if (collectionRepository.findById(id).isPresent()) {
            collection = collectionRepository.findById(id).get();
        }
        return collection;
    }
}
