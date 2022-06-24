package com.bekzodkeldiyarov.collectionstore.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Item extends BaseEntity {
    private String name;
//    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "collection_id", referencedColumnName = "id", nullable = false)
    private Collection collection;


    @Builder
    public Item(Long id, String name, Collection collection) {
        super(id);
        this.name = name;
        this.collection = collection;
    }

    @ManyToMany
    @JoinTable(
            name = "item_attribute",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "attr_id"))
    private Set<Attribute> attributes = new HashSet<>();
}
