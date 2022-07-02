package com.bekzodkeldiyarov.collectionstore.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item extends BaseEntity {
    private String name;

    @ManyToMany
    @JoinTable(name = "item_tags", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "tab_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "collection_id", referencedColumnName = "id", nullable = false)
    private Collection collection;


    @Builder
    public Item(Long id, String name, Collection collection, List<ItemAttributeValue> itemAttributeValues, Set<Tag> tags) {
        super(id);
        this.name = name;
        this.collection = collection;
        this.itemAttributeValues = itemAttributeValues;
        this.tags = tags;
    }

    @OneToMany(mappedBy = "item")
    List<ItemAttributeValue> itemAttributeValues = new ArrayList<>();


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + "id = " + getId() + ", " + "name = " + getName() + ", " + "collection = " + getCollection() + ")";
    }
}
