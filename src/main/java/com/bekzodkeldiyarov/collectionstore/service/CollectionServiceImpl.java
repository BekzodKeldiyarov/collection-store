package com.bekzodkeldiyarov.collectionstore.service;

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

    @Override
    public Collection save(Collection collection) {
        Collection collectionFromDb;
        if (collection.getId() != null) {
            collectionFromDb = collectionRepository.findById(collection.getId()).orElse(null);
            if (collectionFromDb != null) {
                collectionFromDb.setName(collection.getName());
                collectionFromDb.setDescription(collection.getDescription());
            }
        } else {
            collectionFromDb = collection;
        }
        assert collectionFromDb != null;
        return collectionRepository.save(collectionFromDb);
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
