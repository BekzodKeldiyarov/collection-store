package com.bekzodkeldiyarov.collectionstore.converters;

import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.Item;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ItemCommandToItem implements Converter<ItemCommand, Item> {
    @Override
    public Item convert(ItemCommand source) {
        Item item = Item.builder()
                .id(source.getId())
                .name(source.getName())
                .itemAttributeValues(source.getItemAttributeValues())
                .collection(source.getCollection())
                .tags(new HashSet<>(source.getTags()))
                .comments(source.getComments())
                .build();
        return item;
    }
}
