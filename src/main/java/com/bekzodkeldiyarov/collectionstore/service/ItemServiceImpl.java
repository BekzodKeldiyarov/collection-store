package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.converters.CollectionCommandToCollection;
import com.bekzodkeldiyarov.collectionstore.converters.ItemCommandToItem;
import com.bekzodkeldiyarov.collectionstore.converters.ItemToItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.Item;
import com.bekzodkeldiyarov.collectionstore.model.ItemAttributeValue;
import com.bekzodkeldiyarov.collectionstore.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public ItemServiceImpl(ItemRepository itemRepository, ItemCommandToItem itemCommandToItem, ItemToItemCommand itemToItemCommand, CollectionCommandToCollection collectionCommandToCollection, CollectionService collectionService, AttributeService attributeService, ItemAttributeValueService itemAttributeValueService) {
        this.itemRepository = itemRepository;
        this.itemCommandToItem = itemCommandToItem;
        this.itemToItemCommand = itemToItemCommand;
        this.collectionCommandToCollection = collectionCommandToCollection;
        this.collectionService = collectionService;

        this.attributeService = attributeService;
        this.itemAttributeValueService = itemAttributeValueService;
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }


    @Override
    public ItemCommand saveItemCommand(ItemCommand command) {
        Item savedItem = null;
        if (command.getId() == null) {
            Item itemToSave = itemCommandToItem.convert(command);
            assert itemToSave != null : "Item to save is null";
            for (ItemAttributeValue itemAttributeValue : itemToSave.getItemAttributeValues()) {
                itemAttributeValue.setItem(itemToSave);
            }
            savedItem = itemRepository.save(itemToSave);
            itemAttributeValueService.save(savedItem.getItemAttributeValues());
        } else {
            Optional<Item> optionalItem = itemRepository.findById(command.getId());
            if (optionalItem.isPresent()) {
                Item itemToSave = optionalItem.get();
                itemToSave.setName(command.getName());
                itemToSave.setItemAttributeValues(command.getItemAttributeValues());
                for (ItemAttributeValue itemAttributeValue : itemToSave.getItemAttributeValues()) {
                    ItemAttributeValue itemAttributeValueToSave = itemAttributeValueService.findById(itemAttributeValue.getId());
                    itemAttributeValueToSave.setItem(itemAttributeValue.getItem());
                    itemAttributeValueToSave.setValue(itemAttributeValue.getValue());
                    itemAttributeValue.setAttribute(itemAttributeValue.getAttribute());
                    log.info(itemAttributeValueToSave + "");
                    itemAttributeValueService.save(itemAttributeValueToSave);
                }
                log.info(itemToSave.getItemAttributeValues() + "");
                savedItem = itemRepository.save(itemToSave);
                itemAttributeValueService.save(savedItem.getItemAttributeValues());
            }
        }
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
        log.info("All collections of user" + collectionsOfUser);
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
    public ItemCommand findItemCommandById(Long id) {
        Item item = itemRepository.findById(id).get();

        return itemToItemCommand.convert(item);
    }

}
