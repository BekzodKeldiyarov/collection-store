package com.bekzodkeldiyarov.collectionstore.converters;

import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.Item;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ItemToItemCommand implements Converter<Item, ItemCommand> {
    @Override
    public ItemCommand convert(Item source) {
        ItemCommand item = ItemCommand.builder()
                .id(source.getId())
                .name(source.getName())
                .itemAttributeValues(source.getItemAttributeValues())
                .collection(source.getCollection())
                .tags(new ArrayList<>(source.getTags()))
                .build();
        return item;
    }
}
