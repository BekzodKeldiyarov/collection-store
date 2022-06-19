package com.bekzodkeldiyarov.collectionstore.converters;

import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CollectionCommandToCollection implements Converter<CollectionCommand, Collection> {
    @Override
    public Collection convert(CollectionCommand source) {
        Collection collection = new Collection();
        collection.setName(source.getName());
        collection.setDescription(source.getDescription());
        collection.setId(source.getId());
        collection.setUser(source.getUser());
        collection.setAttributes(source.getAttributes());

        return collection;
    }
}
