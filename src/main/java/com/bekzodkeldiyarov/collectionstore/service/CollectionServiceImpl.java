package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionCommandToCollection;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionToCollectionCommand;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;
    private final CollectionToCollectionCommand collectionToCollectionCommand;
    private final CollectionCommandToCollection collectionCommandToCollection;

    public CollectionServiceImpl(CollectionRepository collectionRepository, CollectionToCollectionCommand collectionToCollectionCommand, CollectionCommandToCollection collectionCommandToCollection) {
        this.collectionRepository = collectionRepository;
        this.collectionToCollectionCommand = collectionToCollectionCommand;
        this.collectionCommandToCollection = collectionCommandToCollection;
    }

    @Override
    public Collection save(Collection collection) {
        return collectionRepository.save(collection);
    }

    @Override
    public CollectionCommand saveCollectionCommand(CollectionCommand collectionCommand) {
        Collection collectionToSave = collectionCommandToCollection.convert(collectionCommand);
        Collection savedCollection = collectionRepository.save(collectionToSave);
        CollectionCommand savedCollectionCommand = collectionToCollectionCommand.convert(savedCollection);
        return savedCollectionCommand;
    }

    @Override
    public List<CollectionCommand> getAllCollectionCommands() {
        List<Collection> collections = collectionRepository.findAll();
        List<CollectionCommand> collectionCommands = new ArrayList<>();
        for (Collection collection :
                collections) {
            collectionCommands.add(collectionToCollectionCommand.convert(collection));
        }
        return collectionCommands;
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

//    ItemCommand(
//            id=null,
//            name=test,
//            collection=Collection(name=My book, description=My books collection, user=User(username=admin, email=admin@gmail.com, isEnabled=true, roles=[Role(name=ROLE_ADMIN)])),
//    itemAttributeValues=[
//            ItemAttributeValue(value=paul,
//                               item=null,
//                               attribute=Attribute(attributeName=Author, type=String, collection=Collection(name=My book, description=My books collection, user=User(username=admin, email=admin@gmail.com, isEnabled=true, roles=[Role(name=ROLE_ADMIN)])))),
//            ItemAttributeValue(value=2333-02-11,
//                               item=null,
//                               attribute=Attribute(attributeName=Published in, type=Date, collection=Collection(name=My book, description=My books collection, user=User(username=admin, email=admin@gmail.com, isEnabled=true, roles=[Role(name=ROLE_ADMIN)]))))])
}
