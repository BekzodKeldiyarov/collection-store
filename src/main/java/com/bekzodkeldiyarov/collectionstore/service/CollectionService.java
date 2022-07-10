package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Collection;

import java.util.List;

public interface CollectionService {
    Collection save(Collection collection);

    List<Collection> getAllCollectionsOfUser(boolean isAdmin, String username);

    List<Collection> getAllCollectionsOfUser(Long userId);

    List<Collection> getBiggestCollections();

    Collection findCollectionById(Long id);

    void deleteCollectionsOfUserById(Long[] ids, Long userId);

    void deleteCollectionById(Long id);
}
