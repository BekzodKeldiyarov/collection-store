package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.converters.AttributeCommandToAttribute;
import com.bekzodkeldiyarov.collectionstore.converters.AttributeToAttributeCommand;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionCommandToCollection;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.repository.AttributeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
class AttributeServiceImplTest {
    AttributeService attributeService;

    @Mock
    AttributeRepository attributeRepository;
    @Mock
    AttributeCommandToAttribute attributeCommandToAttribute;
    @Mock
    AttributeToAttributeCommand attributeToAttributeCommand;
    @Mock
    CollectionCommandToCollection collectionCommandToCollection;
    @Mock
    CollectionService collectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        attributeService = new AttributeServiceImpl(attributeRepository, attributeCommandToAttribute, attributeToAttributeCommand, collectionService, collectionCommandToCollection);
    }

    @Test
    void save() {
        Collection collection = Collection.builder().id(1L).name("books").description("bekzod's book").build();
        AttributeCommand attributeCommand = AttributeCommand.builder().attributeName("Author").type("String").collection(collection).build();
        Attribute attribute = Attribute.builder().id(1L).attributeName("Author").type("String").build();

        when(attributeRepository.save(Mockito.any(Attribute.class))).thenReturn(attribute);

        attributeService.save(attributeCommand);
        assertEquals(Long.valueOf(1L), attribute.getId());
        verify(attributeRepository, times(1)).save(any());

    }


    @Test
    void findAll() {
        List<Attribute> attributes = new ArrayList<>();
        Attribute attribute1 = Attribute.builder().id(1L).build();
        Attribute attribute2 = Attribute.builder().id(1L).build();
        Attribute attribute3 = Attribute.builder().id(1L).build();

        attributes.add(attribute1);
        attributes.add(attribute2);
        attributes.add(attribute3);

        when(attributeRepository.findAll()).thenReturn(attributes);

        List<Attribute> attributesResult = attributeService.findAll();
        assertEquals(3, attributesResult.size());
        verify(attributeRepository, times(1)).findAll();
    }
}