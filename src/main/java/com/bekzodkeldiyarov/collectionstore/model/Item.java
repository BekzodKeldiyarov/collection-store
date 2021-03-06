package com.bekzodkeldiyarov.collectionstore.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Indexed
@Table(name = "item")
@NoArgsConstructor
public class Item extends BaseEntity {
    private String name;

    @ManyToMany
    @JoinTable(name = "item_tags", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "tab_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "collection_id", referencedColumnName = "id", nullable = false)
    @IndexedEmbedded
    private Collection collection;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Item(Long id, String name, Collection collection, List<ItemAttributeValue> itemAttributeValues, Set<Tag> tags, List<Comment> comments, List<Like> likes) {
        super(id);
        this.name = name;
        this.collection = collection;
        this.itemAttributeValues = itemAttributeValues;
        this.tags = tags;
        this.comments = comments;
        this.likes = likes;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    List<ItemAttributeValue> itemAttributeValues = new ArrayList<>();


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + "id = " + getId() + ", " + "name = " + getName() + ", " + "collection = " + getCollection() + ")";
    }


    public void addLikeToItem(Like like) {
        like.setItem(this);
        this.getLikes().add(like);
    }

    public void addItemAttributeValue(ItemAttributeValue itemAttributeValue) {
        this.getItemAttributeValues().add(itemAttributeValue);
        itemAttributeValue.setItem(this);
    }
}



