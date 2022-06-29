package com.bekzodkeldiyarov.collectionstore.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
//    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "collection_id", referencedColumnName = "id", nullable = false)
    private Collection collection;


    @Builder
    public Item(Long id, String name, Collection collection, List<ItemAttributeValue> itemAttributeValues) {
        super(id);
        this.name = name;
        this.collection = collection;
        this.itemAttributeValues = itemAttributeValues;
    }

    @OneToMany(mappedBy = "item")
    List<ItemAttributeValue> itemAttributeValues = new ArrayList<>();


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "name = " + getName() + ", " +
                "collection = " + getCollection() + ")";
    }
}
