package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.*;
import com.bekzodkeldiyarov.collectionstore.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;


    private final CollectionService collectionService;
    private final ItemAttributeValueService itemAttributeValueService;
    private final TagService tagService;

    public ItemServiceImpl(ItemRepository itemRepository, CollectionService collectionService, ItemAttributeValueService itemAttributeValueService, TagService tagService) {
        this.itemRepository = itemRepository;
        this.collectionService = collectionService;
        this.itemAttributeValueService = itemAttributeValueService;
        this.tagService = tagService;
    }

//    @Override
//    public Item save(Item item) {
//        Item savedItem;
//        Item itemToSave = new Item();
//        if (item.getId() == null) {
//            itemToSave = item;
//            for (ItemAttributeValue itemAttributeValue : item.getItemAttributeValues()) {
//                itemAttributeValue.setItem(item);
//            }
//        } else {
//            Optional<Item> optionalItem = itemRepository.findById(item.getId());
//            if (optionalItem.isPresent()) {
//                itemToSave = optionalItem.get();
//                itemToSave.setName(item.getName());
//                itemToSave.setItemAttributeValues(item.getItemAttributeValues());
//                itemToSave.setTags(item.getTags());
//                for (Tag tag : itemToSave.getTags()) {
//                    Tag tagToSave = tagService.findByName(tag.getName());
//                    tagToSave.getItems().add(itemToSave);
//                    tagService.save(tagToSave);
//                }
//                for (ItemAttributeValue itemAttributeValue : itemToSave.getItemAttributeValues()) {
//                    ItemAttributeValue itemAttributeValueToSave = itemAttributeValueService.findById(itemAttributeValue.getId());
//                    itemAttributeValueToSave.setItem(itemAttributeValue.getItem());
//                    itemAttributeValueToSave.setValue(itemAttributeValue.getValue());
//                    itemAttributeValue.setAttribute(itemAttributeValue.getAttribute());
//                    itemAttributeValueService.save(itemAttributeValueToSave);
//                }
//            }
//        }
//        savedItem = itemRepository.save(itemToSave);
//        itemAttributeValueService.save(savedItem.getItemAttributeValues());
//        tagService.save(savedItem.getTags());
//        return savedItem;
//    }
//


    @Override
    public Item save(Item item) {
        Item itemToSave;
        if (item.getId() != null) {
            itemToSave = itemRepository.findById(item.getId()).orElse(null);
        } else {
            itemToSave = new Item();
            itemToSave.setCollection(item.getCollection());
        }
        assert itemToSave != null;
        itemToSave.setName(item.getName());
        itemToSave.setTags(item.getTags());
        itemToSave.setComments(item.getComments());
        itemToSave.setItemAttributeValues(item.getItemAttributeValues());
        itemToSave.setLikes(item.getLikes());
        log.info("item to save: " + itemToSave);
        return itemRepository.save(itemToSave);
    }

    @Override
    public Item getNewItemInstance(Long collectionId) {
        Item item = new Item();
        Collection collection = collectionService.findCollectionById(collectionId);
        Set<Attribute> attributesOfCollection = collection.getAttributes();
        collection.addItemToCollection(item);
        for (Attribute attribute : attributesOfCollection) {
            ItemAttributeValue itemAttributeValue = new ItemAttributeValue();
            itemAttributeValue.setItemOfItemAttributeValue(item);
            itemAttributeValue.setAttributeOfItemAttributeValue(attribute);
        }
        return item;
    }


    @Override
    public Item findItemById(Long id) {
        return itemRepository.findById(id).get();
    }


    @Override
    public Item bindTagsToItem(Item item, String[] tags) {
        if (tags != null) {
            for (String tag : tags) {
                Tag tagFromDb = tagService.findByName(tag);
                if (tagFromDb == null) {
                    tagFromDb = new Tag();
                    tagFromDb.setName(tag);
                    tagService.save(tagFromDb);
                }
                item.getTags().add(tagFromDb);
            }
        }
        return item;
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getItemsOfCollectionId(Long id) {
        return itemRepository.findByCollectionId(id);
    }

    @Override
    public List<Item> getAllItemsSortByLastAdded() {
        return itemRepository.findAllByOrderByIdDesc();
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
}
