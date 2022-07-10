package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.ItemAttributeValue;
import com.bekzodkeldiyarov.collectionstore.repository.ItemAttributeValueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ItemAttributeValueServiceImpl implements ItemAttributeValueService {
    private final ItemAttributeValueRepository repository;

    public ItemAttributeValueServiceImpl(ItemAttributeValueRepository repository) {
        this.repository = repository;
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
