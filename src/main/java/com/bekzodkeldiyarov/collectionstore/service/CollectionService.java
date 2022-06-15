package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Collection;

import java.util.List;

public interface CollectionService {
    Collection save(Collection collection);
    List<Collection> findAll();
}
