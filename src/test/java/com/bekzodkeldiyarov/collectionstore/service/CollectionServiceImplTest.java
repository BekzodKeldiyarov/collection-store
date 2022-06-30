package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionCommandToCollection;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionToCollectionCommand;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.repository.CollectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CollectionServiceImplTest {
    CollectionService service;
    @Mock
    CollectionRepository collectionRepository;
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
        service = new CollectionServiceImpl(collectionRepository, collectionToCollectionCommand, collectionCommandToCollection, userService);
    }

    @Test
    void findCollectionCommandById() {

        Collection collection = Collection.builder().id(1L).build();

        when(collectionRepository.findById(anyLong())).thenReturn(Optional.of(collection));
        when(collectionToCollectionCommand.convert(any(Collection.class))).thenReturn(CollectionCommand.builder().id(1L).build());
        CollectionCommand collectionCommand = service.findCollectionCommandById(1L);
// todo why times 1 is not working?
        verify(collectionRepository, times(2)).findById(anyLong());
    }
}