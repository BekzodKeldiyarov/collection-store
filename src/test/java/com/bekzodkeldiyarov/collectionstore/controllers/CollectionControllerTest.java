package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.converters.CollectionCommandToCollection;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionToCollectionCommand;
import com.bekzodkeldiyarov.collectionstore.repository.CollectionRepository;
import com.bekzodkeldiyarov.collectionstore.service.AttributeService;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.CollectionServiceImpl;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Attr;

class CollectionControllerTest {
    CollectionService service;
    @Mock
    CollectionRepository repository;
    @Mock
    CollectionToCollectionCommand collectionToCollectionCommand;
    @Mock
    CollectionCommandToCollection collectionCommandToCollection;
    @Mock
    UserService userService;
    @Mock
    AttributeService attributeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CollectionServiceImpl(repository, collectionToCollectionCommand, collectionCommandToCollection, userService);
    }

    @Test
    void getAllCollections() {
    }

    @Test
    void getAddNewCollectionPage() {
    }

    @Test
    void addNewCollection() {
    }

    @Test
    void getUpdateCollectionPage() {

    }


}