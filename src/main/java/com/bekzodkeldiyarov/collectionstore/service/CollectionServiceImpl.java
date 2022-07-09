package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionCommandToCollection;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionToCollectionCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.repository.CollectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
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
        Collection collectionToSave;
        CollectionCommand savedCollectionCommand;
        if (collectionCommand.getId() == null) {
            collectionToSave = collectionCommandToCollection.convert(collectionCommand);
        } else {
            collectionToSave = collectionRepository.findById(collectionCommand.getId()).get();
            collectionToSave.setName(collectionCommand.getName());
            collectionToSave.setDescription(collectionCommand.getDescription());
        }
        Collection savedCollection = collectionRepository.save(collectionToSave);
        savedCollectionCommand = collectionToCollectionCommand.convert(savedCollection);
        return savedCollectionCommand;
    }

    @Override
    public CollectionCommand saveCollectionCommand(CollectionCommand collectionCommand, Set<Attribute> attributes) {
        Collection collectionToSave = collectionCommandToCollection.convert(collectionCommand);
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

    @Override
    public void deleteCollectionsOfUserById(Long[] ids, Long userId) {
        User user = userService.findById(userId);
        for (Collection collection : user.getCollections()) {
            for (Long id : ids) {
                if (Objects.equals(collection.getId(), id)) {
                    collectionRepository.deleteById(collection.getId());
                    log.info("Collection with id deleted" + id);
                }
            }
        }
    }

    @Override
    public void deleteCollectionById(Long id) {
        collectionRepository.deleteById(id);
    }
}
