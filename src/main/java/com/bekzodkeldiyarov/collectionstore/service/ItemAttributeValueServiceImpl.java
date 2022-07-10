package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.converters.AttributeCommandToAttribute;
import com.bekzodkeldiyarov.collectionstore.converters.ItemCommandToItem;
import com.bekzodkeldiyarov.collectionstore.converters.ItemToItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.ItemAttributeValue;
import com.bekzodkeldiyarov.collectionstore.repository.ItemAttributeValueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ItemAttributeValueServiceImpl implements ItemAttributeValueService {
    private final ItemAttributeValueRepository repository;
    private final ItemCommandToItem itemCommandToItem;
    private final ItemToItemCommand itemToItemCommand;
    private final AttributeCommandToAttribute attributeCommandToAttribute;

    public ItemAttributeValueServiceImpl(ItemAttributeValueRepository repository, ItemCommandToItem itemCommandToItem, ItemToItemCommand itemToItemCommand, AttributeCommandToAttribute attributeCommandToAttribute) {
        this.repository = repository;
        this.itemCommandToItem = itemCommandToItem;
        this.itemToItemCommand = itemToItemCommand;
        this.attributeCommandToAttribute = attributeCommandToAttribute;
    }

    @Override
    public ItemAttributeValue save(ItemAttributeValue itemAttributeValue) {
        return repository.save(itemAttributeValue);
    }

    @Override
    public List<ItemAttributeValue> save(List<ItemAttributeValue> itemAttributeValues) {
        return repository.saveAll(itemAttributeValues);
    }

    @Override
    public List<ItemAttributeValue> findByItemId(Long id) {
        return repository.findByItemId(id);
    }

    @Override
    public ItemAttributeValue findById(Long id) {
        return repository.findById(id).get();
    }
}
