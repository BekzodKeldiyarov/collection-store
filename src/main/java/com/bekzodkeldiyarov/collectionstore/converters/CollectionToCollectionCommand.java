package com.bekzodkeldiyarov.collectionstore.converters;

import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CollectionToCollectionCommand implements Converter<Collection, CollectionCommand> {
    @Override
    public CollectionCommand convert(Collection source) {
        CollectionCommand collectionCommand = CollectionCommand.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .user(source.getUser())
                .items(source.getItems())
                .attributes(new ArrayList<>(source.getAttributes())).build();

        return collectionCommand;
    }
}
