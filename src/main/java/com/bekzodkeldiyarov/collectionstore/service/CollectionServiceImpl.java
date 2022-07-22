package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.repository.CollectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;

    private final UserService userService;
    @Autowired
    private AttributeService attributeService;

    public CollectionServiceImpl(CollectionRepository collectionRepository, UserService userService) {
        this.collectionRepository = collectionRepository;
        this.userService = userService;
    }

//    @Override
//    public Collection save(Collection collection) {
//        log.info("attributes of collection " + collection.getAttributes());
//        Collection collectionFromDb;
//        if (collection.getId() != null) {
//            collectionFromDb = collectionRepository.findById(collection.getId()).orElse(null);
//            if (collectionFromDb != null) {
//                collectionFromDb.setName(collection.getName());
//                collectionFromDb.setDescription(collection.getDescription());
//                collectionFromDb.setAttributes(new LinkedHashSet<>());
//                for (Attribute attribute : collection.getAttributes()) {
//                    Attribute attributeToSave;
//                    if (attribute.getId() != null) {
//                        attributeToSave = attributeService.findById(attribute.getId());
//                    } else {
//                        attributeToSave = new Attribute();
//                    }
//                    attributeToSave.setCollection(collectionFromDb);
//                    attributeToSave.setAttributeName(attribute.getAttributeName());
//                    attributeToSave.setType(attribute.getType());
//                    Attribute savedAttribute = attributeService.save(attributeToSave);
//                    collectionFromDb.getAttributes().add(savedAttribute);
//                }
//            }
//        } else {
//            collectionFromDb = new Collection();
//        }
//        assert collectionFromDb != null;
//        Collection savedCollection = collectionRepository.save(collectionFromDb);
////        for (Attribute attribute : savedCollection.getAttributes()) {
////            log.info(attribute.toString());
////            Attribute attributeToSave;
////            if (attribute.getId() != null) {
////                attributeToSave = attributeService.findById(attribute.getId());
////                attributeToSave.setAttributeName(attribute.getAttributeName());
////                attributeToSave.setType(attribute.getType());
////            } else {
////                attributeToSave = attribute;
////            }
////            attributeToSave.setCollection(savedCollection);
////            attributeService.save(attributeToSave);
////        }
//        return savedCollection;
//    }
//
//

    @Override
    public Collection save(Collection collection) {
        Collection collectionFromDb;
        if (collection.getId() != null) {
            collectionFromDb = collectionRepository.findById(collection.getId()).orElse(null);
        } else {
            collectionFromDb = new Collection();
        }
        collectionFromDb.setName(collection.getName());
        collectionFromDb.setDescription(collection.getDescription());
        collectionFromDb.setAttributes(new LinkedHashSet<>());
        collectionFromDb.setUser(userService.findByUsername("admin"));
        //todo implement setting user depending on logged in user
        for (Attribute attribute : collection.getAttributes()) {
            Attribute attributeToSave;
            if (attribute.getId() != null) {
                attributeToSave = attributeService.findById(attribute.getId());
            } else {
                attributeToSave = new Attribute();
            }
            attributeToSave.setAttributeName(attribute.getAttributeName());
            attributeToSave.setType(attribute.getType());
            collectionFromDb.addAttributeToCollection(attribute);
        }
        Collection savedCollection = collectionRepository.save(collectionFromDb);
        return savedCollection;
    }


    @Override
    public List<Collection> getAllCollectionsOfUser(boolean isAdmin, String username) {
        List<Collection> collections;
        if (isAdmin) {
            collections = collectionRepository.findAll();
        } else {
            collections = collectionRepository.findAllByUserUsername(username);
        }
        return collections;
    }

    @Override
    public List<Collection> getAllCollectionsOfUser(Long userId) {
        return collectionRepository.findAllByUserId(userId);
    }

    @Override
    public List<Collection> getBiggestCollections() {
        List<Collection> collections = collectionRepository.findAll();
        Collections.sort(collections, new Comparator<Collection>() {
            @Override
            public int compare(Collection o1, Collection o2) {
                return o2.getItems().size() - o1.getItems().size();
            }
        });


        return new ArrayList<>(collections.subList(0, 5));
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCollectionById(Long id) {
        collectionRepository.deleteById(id);
    }
}
