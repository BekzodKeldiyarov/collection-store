package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionCommandToCollection;
import com.bekzodkeldiyarov.collectionstore.converters.ItemCommandToItem;
import com.bekzodkeldiyarov.collectionstore.converters.ItemToItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.*;
import com.bekzodkeldiyarov.collectionstore.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemCommandToItem itemCommandToItem;
    private final ItemToItemCommand itemToItemCommand;

    private final CollectionCommandToCollection collectionCommandToCollection;

    private final CollectionService collectionService;
    private final AttributeService attributeService;
    private final ItemAttributeValueService itemAttributeValueService;
    private final TagService tagService;

    public ItemServiceImpl(ItemRepository itemRepository, ItemCommandToItem itemCommandToItem, ItemToItemCommand itemToItemCommand, CollectionCommandToCollection collectionCommandToCollection, CollectionService collectionService, AttributeService attributeService, ItemAttributeValueService itemAttributeValueService, TagService tagService) {
        this.itemRepository = itemRepository;
        this.itemCommandToItem = itemCommandToItem;
        this.itemToItemCommand = itemToItemCommand;
        this.collectionCommandToCollection = collectionCommandToCollection;
        this.collectionService = collectionService;

        this.attributeService = attributeService;
        this.itemAttributeValueService = itemAttributeValueService;
        this.tagService = tagService;
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }


    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')" + "or authentication.principal.equals(#command.member) ")
    public ItemCommand saveItemCommand(ItemCommand command) {
        Item savedItem = new Item();
        Item itemToSave = new Item();
        if (command.getId() == null) {
            itemToSave = itemCommandToItem.convert(command);
            assert itemToSave != null : "Item to save is null";
            for (ItemAttributeValue itemAttributeValue : itemToSave.getItemAttributeValues()) {
                itemAttributeValue.setItem(itemToSave);
            }
        } else {
            Optional<Item> optionalItem = itemRepository.findById(command.getId());
            if (optionalItem.isPresent()) {
                itemToSave = optionalItem.get();
                itemToSave.setName(command.getName());
                itemToSave.setItemAttributeValues(command.getItemAttributeValues());
                itemToSave.setTags(new HashSet<>(command.getTags()));
                for (Tag tag : itemToSave.getTags()) {
                    Tag tagToSave = tagService.findByName(tag.getName());
                    tagToSave.getItems().add(itemToSave);
                    tagService.save(tagToSave);
                }
                for (ItemAttributeValue itemAttributeValue : itemToSave.getItemAttributeValues()) {
                    ItemAttributeValue itemAttributeValueToSave = itemAttributeValueService.findById(itemAttributeValue.getId());
                    itemAttributeValueToSave.setItem(itemAttributeValue.getItem());
                    itemAttributeValueToSave.setValue(itemAttributeValue.getValue());
                    itemAttributeValue.setAttribute(itemAttributeValue.getAttribute());
                    itemAttributeValueService.save(itemAttributeValueToSave);
                }
            }
        }

        savedItem = itemRepository.save(itemToSave);
        itemAttributeValueService.save(savedItem.getItemAttributeValues());
        tagService.save(savedItem.getTags());
        return itemToItemCommand.convert(savedItem);
    }


    @Override
    public List<ItemCommand> getAllItemsOfCollection(Long id) {
        List<Item> items = itemRepository.findByCollectionId(id);
        List<ItemCommand> itemCommands = new ArrayList<>();
        for (Item item : items) {
            itemCommands.add(itemToItemCommand.convert(item));
        }
        return itemCommands;
    }

    @Override
    public List<ItemCommand> getAllItemsOfUser(Long userId) {
        List<Collection> collectionsOfUser = collectionService.getAllCollectionsOfUser(userId);
        List<Item> items = new ArrayList<>();
        List<ItemCommand> itemsToReturn = new ArrayList<>();
        for (Collection collection : collectionsOfUser) {
            items.addAll(itemRepository.findByCollectionId(collection.getId()));
        }
        for (Item item : items) {
            itemsToReturn.add(itemToItemCommand.convert(item));
        }
        return itemsToReturn;
    }

    @Override
    public ItemCommand getNewItemCommandInstance(Long collectionId) {
        Collection collection = collectionService.findCollectionById(collectionId);
        List<Attribute> attributesForCollection = attributeService.getAllAttributesOfCollection(collectionId);
        Item item = new Item();
        for (Attribute attribute : attributesForCollection) {

            ItemAttributeValue itemAttributeValue = new ItemAttributeValue();
            itemAttributeValue.setItem(item);
            item.getItemAttributeValues().add(itemAttributeValue);

            itemAttributeValue.setAttribute(attribute);
            attribute.getItemAttributeValues().add(itemAttributeValue);
        }
        item.setCollection(collection);

        return itemToItemCommand.convert(item);
    }


    @Override
    public Item findItemById(Long id) {
        return itemRepository.findById(id).get();
    }

    @Override
    public ItemCommand findItemCommandById(Long id) {
        return itemToItemCommand.convert(findItemById(id));
    }

    @Override
    public ItemCommand bindTagsToItemCommand(ItemCommand itemCommand, String[] tags) {
        itemCommand.setTags(new ArrayList<>());
        for (String tag : tags) {
            Tag tagFromDb = tagService.findByName(tag);
            if (tagFromDb == null) {
                tagFromDb = new Tag();
                tagFromDb.setName(tag);
                tagService.save(tagFromDb);
            }
            itemCommand.getTags().add(tagFromDb);
        }
        return itemCommand;
    }

    @Override
    public List<ItemCommand> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemCommand> itemsToReturn = new ArrayList<>();
        for (Item item : items) {
            itemsToReturn.add(itemToItemCommand.convert(item));
        }
        return itemsToReturn;
    }
}
